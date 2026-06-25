package com.example.koza

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [OglasEntity::class], version = 1, exportSchema = false)
abstract class KozaDatabase : RoomDatabase() {
    abstract fun oglasDao(): OglasDao

    companion object {
        @Volatile
        private var INSTANCE: KozaDatabase? = null

        fun getDatabase(context: Context): KozaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    KozaDatabase::class.java,
                    "koza_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
