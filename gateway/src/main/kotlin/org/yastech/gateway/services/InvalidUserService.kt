package org.yastech.gateway.services

import org.springframework.stereotype.Service
import org.yastech.gateway.docs.InvalidUser
import org.yastech.gateway.docs.InvalidUserRepository
import org.yastech.gateway.docs.User
import org.yastech.gateway.docs.UserRepository
import org.yastech.gateway.utils.PasswordEncoder
import java.time.Duration
import java.time.LocalDateTime

@Service
class InvalidUserService
(
    private val invalidUserRepository: InvalidUserRepository,
    private val passwordEncoder: PasswordEncoder
)
{
    fun exists(email: String) = invalidUserRepository.existsByEmail(email)

    fun get(email: String) = invalidUserRepository.findByEmail(email)

    fun save(user: InvalidUser): InvalidUser
    {
        user.password = passwordEncoder.encode(user.password)
        return invalidUserRepository.save(user)
    }

    fun delete(email: String) = invalidUserRepository.deleteByEmail(email)
}