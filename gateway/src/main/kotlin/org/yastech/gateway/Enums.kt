package org.yastech.gateway

enum class GENDER
{
    MALE,
    FEMALE,
    OTHER
}

enum class CRASH(val value: String)
{
    SEND_EMAIL_ERROR("SendingEmailError")
}

enum class ERROR
{
    WARNING,
    INFO,
    ERROR
}