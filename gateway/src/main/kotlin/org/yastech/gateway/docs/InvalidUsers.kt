package org.yastech.gateway.docs

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.MongoRepository
import org.yastech.gateway.GENDER
import java.time.LocalDate
import java.time.LocalDateTime

@Document
data class InvalidUser
(
    @Id
    val id: String?,
    var email: String,
    var firstname: String,
    var lastname: String,
    var birthday: LocalDate,
    @Indexed(name = "expireAt", expireAfterSeconds = 0)
    var expireAt: LocalDateTime,
    var gender: GENDER,
    var password: String,
    var requestTimes: Int
)

interface InvalidUserRepository: MongoRepository<InvalidUser, String>
{
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): InvalidUser
    fun deleteByEmail(email: String)
}