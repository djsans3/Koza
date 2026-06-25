package com.example.koza

object UserSession {
    var isGuest: Boolean = false
    var userName: String = "Pero Perić"
    var userEmail: String = "pero@email.com"

    fun setGuestMode() {
        isGuest = true
        userName = "Gost"
        userEmail = ""
    }

    fun setAdminMode() {
        isGuest = false
        userName = "Pero Perić"
        userEmail = "admin@koza.hr"
    }
}
