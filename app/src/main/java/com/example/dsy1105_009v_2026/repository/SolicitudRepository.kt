package com.example.dsy1105_009v_2026.repository

import com.example.dsy1105_009v_2026.data.SolicitudDao
import com.example.dsy1105_009v_2026.model.SolicitudEntity
import kotlinx.coroutines.flow.Flow

class SolicitudRepository(private val dao: SolicitudDao) {

    fun observarPorCliente(cliente: String): Flow<List<SolicitudEntity>> =
        dao.observarPorCliente(cliente)

    suspend fun guardar(solicitud: SolicitudEntity): SolicitudEntity {
        val id = dao.insertar(solicitud)
        return solicitud.copy(id = id.toInt())
    }

    suspend fun buscarPorId(id: Int, cliente: String?): SolicitudEntity? =
        dao.obtenerPorId(id, cliente)
}