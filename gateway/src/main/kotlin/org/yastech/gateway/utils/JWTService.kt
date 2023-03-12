package org.yastech.gateway.utils

import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.sql.Date
import java.time.LocalDate
import javax.crypto.SecretKey

@Service
class JWTService {
    private val logger: Logger = LoggerFactory.getLogger(JWTService::class.java)

    private val key: SecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256)

    fun generateTokenFromID(id: String?): String?
    {
        return Jwts.builder()

            .setSubject(id)

            .setIssuedAt(Date.valueOf(LocalDate.now()))

            .setExpiration(Date.valueOf(LocalDate.now().plusDays(1)))

            .signWith(key)

            .compact()
    }
    fun generateRefreshFromID(id: String?): String?
    {
        return Jwts.builder()

            .setSubject(id)

            .setIssuedAt(Date.valueOf(LocalDate.now()))

            .setExpiration(Date.valueOf(LocalDate.now().plusDays(60)))

            .signWith(key)

            .compact()
    }

    fun getIDFromJwtToken(token: String?): String? = Jwts.parserBuilder()
        .setSigningKey(key).build().parseClaimsJws(token).body.subject

    fun validateJwtToken(token: String?): Boolean
    {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body.subject
            return true
        }

        catch (e: SignatureException)
        { logger.error("Invalid JWT signature: {}", e.message) }

        catch (e: MalformedJwtException)
        { logger.error("Invalid JWT token: {}", e.message) }

        catch (e: ExpiredJwtException)
        { logger.error("JWT token is expired: {}", e.message) }

        catch (e: UnsupportedJwtException)
        { logger.error("JWT token is unsupported: {}", e.message) }

        catch (e: IllegalArgumentException)
        { logger.error("JWT claims string is empty: {}", e.message) }

        return false
    }
}