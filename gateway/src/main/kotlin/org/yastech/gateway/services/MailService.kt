package org.yastech.gateway.services

import org.springframework.stereotype.Service
import org.yastech.gateway.then
import org.yastech.gateway.utils.EnvHelper
import org.yastech.gateway.utils.HttpClient

@Service
class MailService
(
    private val http: HttpClient,
    private val envHelper: EnvHelper
)
{
    fun sendRegisterEmail(firstname: String, id: String, email: String): String
    {
        val url = envHelper.readEnv("MAIL_SERVICE") ?: "http://localhost:8000"
        val validateUrl = ((envHelper.readEnv("DEVELOPMENT") == "true") then
                "http://localhost") ?: envHelper.readEnv("PROJECT_URL")

        return http.post("$url/mail", null, mutableMapOf(
            "subject" to "TakiTaki registration!",
            "message" to "Hi ${firstname}!\nWelcome to TakiTaki. Your account has been created but not activated. " +
                    "Please click on link below to active your account. \n\n" +
                    "$validateUrl/?res=validate&id=$id",
            "to" to email
        )).toString()
    }
}