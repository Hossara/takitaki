package org.yastech.gateway.api

import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.yastech.gateway.docs.User
import org.yastech.gateway.models.RegisterUser
import org.yastech.gateway.services.UserService
import org.yastech.gateway.utils.*
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.time.LocalDate
import java.time.LocalDateTime

@RestController
@RequestMapping("/auth")
class AuthApi
(
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JWTService,
    private val generator: Generator
)
{
    @PostMapping("/login")
    fun login(@RequestParam email: String, @RequestParam password: String): Mono<MutableMap<String, String>>
    {
        if (isValidEmail(email))
            return mutableMapOf("status" to "error", "code" to "email_inv").toMono()

        return if(userService.exists(email))
        {
            val user: User?

            try
            { user = userService.get(email) }

            catch (e: Exception)
            { return mutableMapOf("status" to "error", "code" to "up_inc").toMono() }

            if (passwordEncoder.check(password, user.password))
            {
                try {
                    val access = jwtService.generateTokenFromID(user.id)!!
                    val refresh = jwtService.generateRefreshFromID(user.id)!!

                    mutableMapOf(
                        "status" to "success",
                        "access" to access,
                        "refresh" to refresh
                    ).toMono()
                }

                catch (e: Exception)
                { return mutableMapOf("status" to "error", "code" to "tkn_cr_err").toMono() }
            }
            else mutableMapOf("status" to "error", "code" to "up_inc").toMono()
        }
        else mutableMapOf("status" to "error", "code" to "up_inc").toMono()
    }

    @PostMapping("/register")
    @CrossOrigin(origins = ["*"])
    fun register(
        @ModelAttribute user: RegisterUser,
        bindingResult: BindingResult
    ): Mono<out MutableMap<String, out Any>>
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

                val saved = userService.save(User(
                    id = null,
                    createdAt = LocalDateTime.now(),
                    email = valid.email,
                    firstname = valid.firstname,
                    lastname = valid.lastname,
                    birthday = birthday,
                    gender = valid.gender,
                    password = valid.password,
                    valid = false,
                    username = generator.generateUUID()
                ))

                mutableMapOf("status" to "success", "id" to saved.id!!).toMono()
            }
            else validate.toMono()
        }
        else mutableMapOf("status" to "error", "code" to "us_exi").toMono()
    }
}