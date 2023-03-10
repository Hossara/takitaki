package org.yastech.gateway.services

import org.springframework.stereotype.Service
import org.yastech.gateway.docs.User
import org.yastech.gateway.docs.UserRepository

@Service
class UserService
(private val userRepository: UserRepository)
{
    fun exists(email: String): Boolean =
        userRepository.existsUserByEmail(email).block()!!

    fun get(email: String): User =
        userRepository.findUserByEmail(email).block()!!
}