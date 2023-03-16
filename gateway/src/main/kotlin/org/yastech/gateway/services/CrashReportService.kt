package org.yastech.gateway.services

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.yastech.gateway.models.CrashModel
import org.yastech.gateway.utils.EnvHelper
import org.yastech.gateway.utils.HttpClient
@Service
class CrashReportService
(
    private val http: HttpClient,
    private val envHelper: EnvHelper
)
{
    private val logger: Logger = LoggerFactory.getLogger(HttpClient::class.java)

    fun sendCrashReport(detail: CrashModel)
    {
        val url = envHelper.readEnv("CRASH_SERVICE") ?: "http://localhost:8001"

        val res = http.post("$url/report", mutableMapOf(
            "err" to detail.error.value,
            "msg" to detail.message,
            "type" to detail.type
        ), null)

        logger.info("CrashReport sent with this response: $res")
    }
}