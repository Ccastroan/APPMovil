package com.example.dsy1105_009v_2026.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "solicitudes")
data class SolicitudEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val cliente: String,
    val titulo: String,
    val descripcion: String,
    val categoria: Categoria,
    val servicio: String,
    val prioridad: Prioridad? = null,
    val estado: EstadoSolicitud = EstadoSolicitud.RECIBIDA,
    val responsable: String? = null,
    val creadaEn: Long = System.currentTimeMillis(),
    val actualizadaEn: Long = System.currentTimeMillis()
) {
    // "SOL-0001": se calcula
    val codigo: String get() = "SOL-" + id.toString().padStart(4, '0')
}