package org.yastech.gateway.utils

import org.springframework.stereotype.Service
import java.util.*

@Service
class Generator
{
    fun generateUUID(): String = UUID.randomUUID().toString()
}