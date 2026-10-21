package com.example.dsy1105_009v_2026.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.dsy1105_009v_2026.model.SolicitudEntity
import kotlinx.coroutines.flow.Flow
@Dao
interface SolicitudDao {

    @Insert
    suspend fun insertar(solicitud: SolicitudEntity): Long

    @Query("SELECT * FROM solicitudes WHERE cliente = :cliente ORDER BY creadaEn DESC")
    fun observarPorCliente(cliente: String): Flow<List<SolicitudEntity>>

    // Lo usa el lector QR (Paso 3). Si cliente es null, busca entre todas las solicitudes.
    @Query("SELECT * FROM solicitudes WHERE id = :id AND (:cliente IS NULL OR cliente = :cliente) LIMIT 1")
    suspend fun obtenerPorId(id: Int, cliente: String?): SolicitudEntity?
}