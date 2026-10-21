package com.example.dsy1105_009v_2026.model

// No es la tabla de la base de datos (esa es SolicitudEntity).
data class SolicitudUiState(
    val titulo: String = "",
    val descripcion: String = "",
    val categoria: Categoria? = null,   // null = todavía no elige
    val servicio: String = "",
    val guardando: Boolean = false,     // true mientras se guarda
    val errorGuardado: String? = null,  // mensaje si falló
    val errores: SolicitudErrores = SolicitudErrores(),
    val ultimaSolicitud: SolicitudEntity? = null
)