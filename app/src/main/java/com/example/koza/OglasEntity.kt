package com.example.koza

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "oglasi")
data class OglasEntity(
    @PrimaryKey val id: String,
    val naziv: String,
    val opis: String,
    val cijena: Double,
    val lokacija: String,
    val kategorija: String,
    val velicina: String,
    val stanje: String,
    val korisnikId: String,
    val korisnikIme: String,
    val slikaUrl: String,
    val wishlistCount: Int,
    val datum: Long,
    val isFavorite: Boolean = false,
    val isMyAd: Boolean = false
)
