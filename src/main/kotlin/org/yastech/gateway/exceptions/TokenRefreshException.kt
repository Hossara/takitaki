package org.yastech.gateway.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.FORBIDDEN)
class TokenRefreshException() :
    RuntimeException("Refresh token expired") {
    companion object {
        private const val serialVersionUID = 1L
    }
}