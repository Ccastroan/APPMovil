package com.example.dsy1105_009v_2026.model

data class QrResult(
    val content: String,                    // texto leído del QR
    val solicitud: SolicitudEntity? = null  // solicitud encontrada en Room
)