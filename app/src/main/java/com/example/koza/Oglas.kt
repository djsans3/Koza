package com.example.koza

data class Oglas(
    val id: String = "",
    val naziv: String = "",
    val opis: String = "",
    val cijena: Double = 0.0,
    val lokacija: String = "",
    val kategorija: String = "",
    val velicina: String = "",       // npr. "M", "42"
    val stanje: String = "",          // npr. "Kao novo"
    val korisnikId: String = "",
    val korisnikIme: String = "",
    val slikaUrl: String = "",
    val wishlistCount: Int = 0,
    val datum: Long = System.currentTimeMillis()
)
