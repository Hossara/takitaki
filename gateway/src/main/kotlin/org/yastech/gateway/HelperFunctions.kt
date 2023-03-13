package org.yastech.gateway

import java.net.URLEncoder

fun String.utf8(): String = URLEncoder.encode(this, "UTF-8")

fun String.isNotInt() = toIntOrNull() == null

infix fun <T> Boolean.then(param: T): T? = if (this) param else null