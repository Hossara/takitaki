package org.yastech.gateway.services

import org.springframework.stereotype.Service
import org.yastech.gateway.utils.HttpClient

@Service
class MailService
(private val http: HttpClient)
{
    fun sendRegisterEmail(firstname: String, id: String, email: String): String
    {
         return http.post("http://localhost:8000/mail", null, mutableMapOf(
            "subject" to "TakiTaki registration!",
            "message" to "Hi ${firstname}!\nWelcome to TakiTaki. Your account has been created but not activated. " +
                    "Please click on link below to active your account. ",
            "to" to email
        )).toString()
    }
}