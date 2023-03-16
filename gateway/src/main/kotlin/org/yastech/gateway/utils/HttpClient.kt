package org.yastech.gateway.utils

import org.springframework.stereotype.Service
import com.fasterxml.jackson.databind.ObjectMapper
import org.yastech.gateway.then
import org.yastech.gateway.utf8
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

@Service
class HttpClient
{
    private fun urlQueryBuilder(query: MutableMap<String, Any>): String =
        query.map {(k, v) -> "${(k.utf8())}=${v.toString().utf8()}"}
            .joinToString("&")

    fun get(url: String, query: MutableMap<String, Any> = mutableMapOf()): Any
    {
        val client = HttpClient.newBuilder().build()

        val request = HttpRequest.newBuilder()
            .uri(URI.create("$url${
                (query.isNotEmpty() then "?${urlQueryBuilder(query)}") ?: ""
            }"))
            .version(HttpClient.Version.HTTP_1_1)
            .build()

        return client.send(request, HttpResponse.BodyHandlers.ofString()).body()
    }

    fun post(
        url: String,
        query: MutableMap<String, Any>?,
        body: MutableMap<String, Any>?
    ): Any
    {
        val client = HttpClient.newBuilder().build()

        val request = HttpRequest.newBuilder()
            .uri(URI.create(
                if(query == null) url
                else "$url?${urlQueryBuilder(query)}"
            ))
            .version(HttpClient.Version.HTTP_1_1)
            .POST(
                if (body == null) HttpRequest.BodyPublishers.noBody()
                else HttpRequest.BodyPublishers.ofString(
                    ObjectMapper().writeValueAsString(body)
                )
            ).build()

        return client.send(request, HttpResponse.BodyHandlers.ofString()).body()
    }
}