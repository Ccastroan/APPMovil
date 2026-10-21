package com.example.dsy1105_009v_2026.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dsy1105_009v_2026.model.SolicitudEntity

@Database(entities = [SolicitudEntity::class], version = 1, exportSchema = false)
abstract class SolicitudDatabase : RoomDatabase() {

    abstract fun solicitudDao(): SolicitudDao

    companion object {
        @Volatile
        private var instancia: SolicitudDatabase? = null

        fun getDatabase(context: Context): SolicitudDatabase =
            instancia ?: synchronized(this) {
                instancia ?: Room.databaseBuilder(
                    context.applicationContext,
                    SolicitudDatabase::class.java,
                    "solicitud_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { instancia = it }
            }
    }
}