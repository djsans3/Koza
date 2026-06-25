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
    val datum: Long = System.currentTimeMillis(),
    val isFavorite: Boolean = false,
    val isMyAd: Boolean = false
)

fun Oglas.toEntity(): OglasEntity {
    return OglasEntity(
        id = id,
        naziv = naziv,
        opis = opis,
        cijena = cijena,
        lokacija = lokacija,
        kategorija = kategorija,
        velicina = velicina,
        stanje = stanje,
        korisnikId = korisnikId,
        korisnikIme = korisnikIme,
        slikaUrl = slikaUrl,
        wishlistCount = wishlistCount,
        datum = datum,
        isFavorite = isFavorite,
        isMyAd = isMyAd
    )
}

fun OglasEntity.toDomain(): Oglas {
    return Oglas(
        id = id,
        naziv = naziv,
        opis = opis,
        cijena = cijena,
        lokacija = lokacija,
        kategorija = kategorija,
        velicina = velicina,
        stanje = stanje,
        korisnikId = korisnikId,
        korisnikIme = korisnikIme,
        slikaUrl = slikaUrl,
        wishlistCount = wishlistCount,
        datum = datum,
        isFavorite = isFavorite,
        isMyAd = isMyAd
    )
}
