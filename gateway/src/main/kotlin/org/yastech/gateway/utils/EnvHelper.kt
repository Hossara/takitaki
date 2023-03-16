package org.yastech.gateway.utils

import io.github.cdimascio.dotenv.dotenv
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class EnvHelper
{
    private val logger: Logger = LoggerFactory.getLogger(EnvHelper::class.java)

    fun readEnv(name: String): String?
    {
        return try
        {
            if(!System.getenv(name).isNullOrEmpty())
                System.getenv(name)
            else if (!dotenv()[name].isNullOrEmpty())
                dotenv()[name]
            else null
        }
        catch (e: Exception)
        {
            logger.error(e.message)
            null
        }
    }
}