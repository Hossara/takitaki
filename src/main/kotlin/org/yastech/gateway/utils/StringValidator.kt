package org.yastech.gateway.utils

import java.util.regex.Pattern


fun isValidEmail(email: String): Boolean {
    val pattern = ("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")

    return Pattern.compile(pattern)
        .matcher(email)
        .matches()
}