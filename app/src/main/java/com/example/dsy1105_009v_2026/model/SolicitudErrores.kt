package com.example.dsy1105_009v_2026.model

// Errores de cada campo del formulario (null = el campo no tiene error)
data class SolicitudErrores(
    val titulo: String? = null,
    val descripcion: String? = null,
    val categoria: String? = null,
    val servicio: String? = null
)