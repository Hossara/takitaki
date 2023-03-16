package org.yastech.gateway.utils

import org.yastech.gateway.GENDER
import org.yastech.gateway.isNotInt
import org.yastech.gateway.models.RegisterUser
import java.time.LocalDate

fun validateFields(user: RegisterUser): MutableMap<String, Any>
{
    val birthday = user.birthday.split("/")

    if (user.firstname.isEmpty() || user.firstname.length > 100 ||
        user.lastname.isEmpty() || user.lastname.length > 100
    ) return mutableMapOf(
        "status" to "error",
        "msg" to "Firstname and lastname length must between 1 and 100!"
    )

    if (
        birthday.size != 3 || birthday[0].length != 2 ||
        birthday[1].length != 2 || birthday[2].length != 4 ||
        birthday[0].isNotInt() || birthday[1].isNotInt() || birthday[2].isNotInt() ||
        birthday[0].toInt() == 0 || birthday[1].toInt() == 0 || birthday[2].toInt() < 1900 ||
        birthday[0].toInt() > 31 || birthday[1].toInt() > 12 ||
        birthday[2].toInt() > LocalDate.now().year
    ) return mutableMapOf(
        "status" to "error",
        "msg" to "Date is invalid! format: dd/mm/yyyy"
    )

    if (!GENDER.values().contentToString().contains(user.gender)) return mutableMapOf(
        "status" to "error",
        "msg" to "Gender invalid!"
    )

    user.email = user.email.trim()
    user.password = user.password.trim()

    if (!isValidEmail(user.email)) return mutableMapOf(
        "status" to "error",
        "msg" to "Email is invalid!"
    )

    if (user.password.length < 3 || user.password.length > 50) return mutableMapOf(
        "status" to "error",
        "msg" to "Password length must between 3 and 50"
    )

    return mutableMapOf("status" to "success", "user" to user)
}