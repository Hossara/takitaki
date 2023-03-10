package org.yastech.gateway.docs

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.yastech.gateway.GENDER
import reactor.core.publisher.Mono
import java.time.LocalDate

@Document
class User
(
    @Id
    val id: String,
    var email: String,
    var firstname: String,
    var lastname: String,
    var birthday: LocalDate,
    var gender: GENDER,
    var password: String
)

interface UserRepository: ReactiveMongoRepository<User, String>
{
    fun existsUserByEmail(email: String): Mono<Boolean>
    fun findUserByEmail(email: String): Mono<User>
}