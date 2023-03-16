package org.yastech.gateway.models

import org.yastech.gateway.GENDER

data class RegisterUser
(
    var email: String,
    var firstname: String,
    var lastname: String,
    var birthday: String,
    var gender: String,
    var password: String
)