package org.yastech.gateway.services

import org.springframework.stereotype.Service
import org.yastech.gateway.docs.User
import org.yastech.gateway.docs.UserRepository
import org.yastech.gateway.utils.PasswordEncoder

@Service
class UserService
(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
)
{
    fun exists(email: String) = userRepository.existsByEmail(email)

    fun get(email: String) = userRepository.findByEmail(email)

    fun save(user: User): User
    {
        user.password = passwordEncoder.encode(user.password)
        return userRepository.save(user)
    }
}