package org.yastech.gateway.utils

import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Component
import java.util.function.Predicate

@Component
class RouterValidator {
    var isSecured: Predicate<ServerHttpRequest> = Predicate<ServerHttpRequest> { request ->
        openApiEndpoints
            .stream()
            .noneMatch { uri: String ->
                request.uri.path.contains(uri)
            }
    }

    companion object {
        val openApiEndpoints = listOf(
            "/register",
            "/login"
        )
    }
}