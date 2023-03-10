package org.yastech.gateway.utils

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class PasswordEncoder
{
    fun encode(password: String): String =
        BCryptPasswordEncoder().encode(password)

    fun check(password: String, encoded: String): Boolean =
        BCryptPasswordEncoder().matches(password, encoded)
}