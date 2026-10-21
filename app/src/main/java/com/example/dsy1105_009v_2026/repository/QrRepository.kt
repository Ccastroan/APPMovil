package com.example.dsy1105_009v_2026.repository

import com.example.dsy1105_009v_2026.model.QrResult

class QrRepository(private val solicitudes: SolicitudRepository) {

   suspend fun processQrContent(content: String, cliente: String): QrResult {
        val id = extraerId(content)
        val solicitud = if (id != null) solicitudes.buscarPorId(id, cliente) else null
        return QrResult(content, solicitud)
    }

    private fun extraerId(content: String): Int? {
        val formato = Regex("""^SOL-(\d{1,9})$""", RegexOption.IGNORE_CASE)
        return formato.find(content.trim())?.groupValues?.get(1)?.toIntOrNull()
    }
}