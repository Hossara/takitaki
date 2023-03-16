package org.yastech.gateway.api

import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.yastech.gateway.CRASH
import org.yastech.gateway.ERROR
import org.yastech.gateway.GENDER
import org.yastech.gateway.docs.InvalidUser
import org.yastech.gateway.docs.User
import org.yastech.gateway.models.CrashModel
import org.yastech.gateway.models.RegisterUser
import org.yastech.gateway.services.CrashReportService
import org.yastech.gateway.services.InvalidUserService
import org.yastech.gateway.services.MailService
import org.yastech.gateway.services.UserService
import org.yastech.gateway.utils.*
import java.time.LocalDate
import java.time.LocalDateTime

@RestController
@RequestMapping("/auth")
class AuthApi
(
    private val userService: UserService,
    private val invalidUserService: InvalidUserService,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JWTService,
    private val mail: MailService,
    private val crashReport: CrashReportService,
    private val generator: Generator
)
{
    @PostMapping("/login")
    fun login(@RequestParam email: String, @RequestParam password: String): MutableMap<String, String>
    {
        if (isValidEmail(email))
            return mutableMapOf("status" to "error", "code" to "email_inv")

        return if(userService.exists(email))
        {
            val user: User?

            try
            { user = userService.get(email) }

            catch (e: Exception)
            { return mutableMapOf("status" to "error", "code" to "up_inc") }

            if (passwordEncoder.check(password, user.password))
            {
                try {
                    val access = jwtService.generateTokenFromID(user.id)!!
                    val refresh = jwtService.generateRefreshFromID(user.id)!!

                    mutableMapOf(
                        "status" to "success",
                        "access" to access,
                        "refresh" to refresh
                    )
                }

                catch (e: Exception)
                { return mutableMapOf("status" to "error", "code" to "tkn_cr_err") }
            }
            else mutableMapOf("status" to "error", "code" to "up_inc")
        }
        else mutableMapOf("status" to "error", "code" to "up_inc")
    }

    @PostMapping("/register")
    @CrossOrigin(origins = ["*"])
    fun register(
        @ModelAttribute user: RegisterUser,
        bindingResult: BindingResult
    ): MutableMap<String, out Any>
    {
        return if(!userService.exists(user.email))
        {
            val validate = validateFields(user)

            if (validate["status"] == "success")
            {
                val valid = validate["user"] as RegisterUser
                val birthdayArray = valid.birthday.split("/")

                val birthday = LocalDate.of(
                    birthdayArray[2].toInt(),
                    birthdayArray[1].toInt(),
                    birthdayArray[0].toInt()
                )

                if(invalidUserService.exists(valid.email))
                {
                    val data = invalidUserService.get(valid.email)

                    if(data.requestTimes == 3)
                    {
                        data.expireAt = LocalDateTime.now().plusHours(1)
                        mail.sendRegisterEmail(data.firstname ,data.id!!, data.email)

                        invalidUserService.save(data)

                        return mutableMapOf(
                            "status" to "error",
                            "code" to "too_req",
                            "msg" to "Your number of requests exceeded the limit. Please try again in 1 hour!"
                        )
                    }
                    else
                    {
                        data.requestTimes = data.requestTimes + 1
                        data.expireAt = LocalDateTime.now().plusSeconds(30)
                        mail.sendRegisterEmail(data.firstname ,data.id!!, data.email)

                        invalidUserService.save(data)

                        return mutableMapOf(
                            "status" to "error",
                            "code" to "n_req",
                            "msg" to "You can make ${
                                if(data.requestTimes == 3) "no" else 3 - data.requestTimes
                            } more requests in less time. Or you can wait 30 seconds for your limit to be removed!"
                        )
                    }
                }

                val saved = invalidUserService.save(InvalidUser(
                    id = null,
                    expireAt = LocalDateTime.now().plusSeconds(30),
                    email = valid.email,
                    firstname = valid.firstname,
                    lastname = valid.lastname,
                    birthday = birthday,
                    gender = GENDER.valueOf(valid.gender),
                    password = valid.password,
                    requestTimes = 1
                ))

                try {
                    mail.sendRegisterEmail(saved.firstname ,saved.id!!, saved.email)

                    mutableMapOf("status" to "success", "id" to saved.id)
                }
                catch (e: Exception)
                {
                    crashReport.sendCrashReport(CrashModel(
                        error = CRASH.SEND_EMAIL_ERROR,
                        message = "Error while sending test email",
                        type = ERROR.ERROR
                    ))

                    mutableMapOf("status" to "error", "code" to "reg_d_ne", "id" to saved.id!!)
                }
            }
            else validate
        }
        else mutableMapOf("status" to "error", "code" to "us_exi")
    }

    @PostMapping("/complete/{email}/{code}")
    fun complete(@PathVariable code: String, @PathVariable email: String): MutableMap<String, String>
    {
        if (invalidUserService.exists(email))
        {
            val user = invalidUserService.get(email)

            if (user.id!! == code)
            {
                invalidUserService.delete(email)

                userService.save(User(
                    id = null,
                    username = generator.generateUUID(),
                    email = user.email,
                    firstname = user.firstname,
                    lastname = user.lastname,
                    birthday = user.birthday,
                    createdAt = LocalDateTime.now(),
                    gender = user.gender,
                    password = user.password
                ))

                return mutableMapOf("status" to "success")
            }
        }

        return mutableMapOf("status" to "error", "code" to "inv_usr")
    }
}