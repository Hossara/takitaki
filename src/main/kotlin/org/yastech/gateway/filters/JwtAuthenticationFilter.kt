package org.yastech.gateway.filters

import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.http.HttpStatus
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.yastech.gateway.utils.JWTService
import org.yastech.gateway.utils.RouterValidator
import reactor.core.publisher.Mono

@RefreshScope
@Component
class AuthenticationFilter
(
    private val routerValidator: RouterValidator,
    private val jwtService: JWTService
)
    : GatewayFilter
{
    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void>
    {
        val request: ServerHttpRequest = exchange.request

        if (routerValidator.isSecured.test(request))
        {
            if (isAuthMissing(request)) return onError(exchange)

            val token = getAuthHeader(request)

            if (!jwtService.validateJwtToken(token)) return onError(exchange)

            populateRequestWithHeaders(exchange, token)
        }
        return chain.filter(exchange)
    }

    private fun onError(exchange: ServerWebExchange): Mono<Void>
    {
        val response: ServerHttpResponse = exchange.response
        response.statusCode = HttpStatus.UNAUTHORIZED
        return response.setComplete()
    }

    private fun getAuthHeader(request: ServerHttpRequest): String =
        request.headers.getOrEmpty("Authorization")[0]

    private fun isAuthMissing(request: ServerHttpRequest): Boolean =
        !request.headers.containsKey("Authorization")

    private fun populateRequestWithHeaders(exchange: ServerWebExchange, token: String)
    {
        exchange.request.mutate()
            .header("id", jwtService.getIDFromJwtToken(token))
            .build()
    }
}