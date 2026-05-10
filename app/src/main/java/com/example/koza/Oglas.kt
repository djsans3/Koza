package com.example.koza

data class Oglas(
    val id: String = "",
    val naziv: String = "",
    val opis: String = "",
    val cijena: Double = 0.0,
    val lokacija: String = "",
    val kategorija: String = "",
    val velicina: String = "",
    val stanje: String = "",
    val korisnikId: String = "",
    val korisnikIme: String = "",
    val slikaUrl: String = "",
    val wishlistCount: Int = 0,
    val datum: Long = System.currentTimeMillis()
)
