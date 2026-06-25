package com.example.koza

class OglasRepository(private val oglasDao: OglasDao) {

    suspend fun getAllOglasi(): List<OglasEntity> = oglasDao.getAllOglasi()

    suspend fun getFavoriteOglasi(): List<OglasEntity> = oglasDao.getFavoriteOglasi()

    suspend fun getMyOglasi(): List<OglasEntity> = oglasDao.getMyOglasi()

    suspend fun getOglasById(id: String): OglasEntity? = oglasDao.getOglasById(id)

    suspend fun insertOglas(oglas: OglasEntity) = oglasDao.insertOglas(oglas)

    suspend fun insertAll(oglasi: List<OglasEntity>) = oglasDao.insertAll(oglasi)

    suspend fun updateOglas(oglas: OglasEntity) = oglasDao.updateOglas(oglas)

    suspend fun deleteOglas(oglas: OglasEntity) = oglasDao.deleteOglas(oglas)

    suspend fun deleteById(id: String) = oglasDao.deleteById(id)
}
