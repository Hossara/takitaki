package org.yastech.gateway.docs

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.MongoRepository
import org.yastech.gateway.GENDER
import java.time.LocalDate
import java.time.LocalDateTime

@Document
data class User
(
    @Id
    val id: String?,
    var username: String?,
    var email: String,
    var firstname: String,
    var lastname: String,
    var birthday: LocalDate,
    var createdAt: LocalDateTime,
    var gender: GENDER,
    var password: String,
    var valid: Boolean?
)

interface UserRepository: MongoRepository<User, String>
{
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): User
}