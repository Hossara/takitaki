package org.yastech.gateway.models

import org.yastech.gateway.CRASH
import org.yastech.gateway.ERROR

data class CrashModel
(
    var error: CRASH,
    var message: String,
    var type: ERROR,
)