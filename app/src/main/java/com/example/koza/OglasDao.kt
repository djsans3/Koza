package com.example.koza

import androidx.room.*

@Dao
interface OglasDao {
    @Query("SELECT * FROM oglasi ORDER BY datum DESC")
    suspend fun getAllOglasi(): List<OglasEntity>

    @Query("SELECT * FROM oglasi WHERE id = :id")
    suspend fun getOglasById(id: String): OglasEntity?

    @Query("SELECT * FROM oglasi WHERE isFavorite = 1 ORDER BY datum DESC")
    suspend fun getFavoriteOglasi(): List<OglasEntity>

    @Query("SELECT * FROM oglasi WHERE isMyAd = 1 ORDER BY datum DESC")
    suspend fun getMyOglasi(): List<OglasEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOglas(oglas: OglasEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(oglasi: List<OglasEntity>)

    @Update
    suspend fun updateOglas(oglas: OglasEntity)

    @Delete
    suspend fun deleteOglas(oglas: OglasEntity)

    @Query("DELETE FROM oglasi WHERE id = :id")
    suspend fun deleteById(id: String)
}
