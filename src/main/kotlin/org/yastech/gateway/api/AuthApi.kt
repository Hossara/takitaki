package org.yastech.gateway.api

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.yastech.gateway.docs.User
import org.yastech.gateway.services.UserService
import org.yastech.gateway.utils.JWTService
import org.yastech.gateway.utils.PasswordEncoder
import org.yastech.gateway.utils.StringValidator
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@RestController
@RequestMapping("/auth")
class AuthApi
(
    private val userService: UserService,
    private val stringValidator: StringValidator,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JWTService
)
{
    @PostMapping("/login")
    fun login(@RequestParam email: String, @RequestParam password: String): Mono<MutableMap<String, String>>
    {
        if (stringValidator.isValidEmail(email))
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
}