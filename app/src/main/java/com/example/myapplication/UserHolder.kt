package com.example.myapplication

import androidx.annotation.VisibleForTesting

object UserHolder {

    private val map = mutableMapOf<String, User>()
    private val phoneFormat =
        Regex("""^\+[0-9]?[\s\-]?\(?[0-9]{3}\)?[\s\-]?[0-9]{3}[\s\-]?\d{2}[\s\-]?[0-9]{2}$""")

    private fun validatePhone(phone: String): Boolean {
        return if (phone.matches(phoneFormat)) true
        else throw IllegalArgumentException("Wrong phone number")
    }

    fun registerUser(
        fullName: String,
        email: String,
        password: String
    ): User = User.makeUser(fullName, email = email, password = password)
        .also { user ->
            if (map.containsKey(user.login)) throw IllegalArgumentException("A user with this email already exists")
            else map[user.login] = user
        }

    fun loginUser(login: String, password: String): String? {
        val phoneLogin = cleanPhone(login)
        return if (phoneLogin.isNotEmpty()) {
            map[phoneLogin]
        } else {
            map[login.trim()]
        }?.let {
            if (it.checkPassword(password)) it.userInfo
            else null
        }
    }


    fun registerUserByPhone(fullName: String, rawPhone: String): User {
        validatePhone(rawPhone)
        return User.makeUser(fullName, phone = rawPhone).also { user ->
            if (map.containsKey(user.phone)) throw IllegalArgumentException("A user with this email already exists")
            else map[user.login] = user
        }
    }

    fun requestAccessCode(phone: String) {
        val phoneLogin = cleanPhone(phone)
        val user = map[phoneLogin]

        user?.let {
            user.updateAccessCode(phone)
        } ?: throw IllegalArgumentException("User with this number doesn't exists")

    }


    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    fun clearHolder() {
        map.clear()
    }

    private fun cleanPhone(phone: String): String {
        return phone.replace("""[^+\d]""".toRegex(), "")
    }
}