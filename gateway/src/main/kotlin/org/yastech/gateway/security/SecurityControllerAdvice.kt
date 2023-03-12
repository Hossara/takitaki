package org.yastech.gateway.security

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.yastech.gateway.exceptions.TokenRefreshException
import java.util.*

@RestControllerAdvice
class SecurityControllerAdvice
{
    @ExceptionHandler(value = [TokenRefreshException::class])
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun handleTokenRefreshException(ex: TokenRefreshException, request: WebRequest): ErrorMessage? {
        return ErrorMessage(
            HttpStatus.FORBIDDEN.value(),
            Date(),
            ex.message!!,
            request.getDescription(false)
        )
    }
}

class ErrorMessage(statusCode: Int, timestamp: Date, message: String, description: String)
{
    private val statusCode: Int
    private val timestamp: Date
    private val message: String
    private val description: String

    init {
        this.statusCode = statusCode
        this.timestamp = timestamp
        this.message = message
        this.description = description
    }
}