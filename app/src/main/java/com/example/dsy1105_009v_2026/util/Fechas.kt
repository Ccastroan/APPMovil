package com.example.dsy1105_009v_2026.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Convierte los milisegundos guardados en Room a un texto
fun Long.comoFechaHora(): String =
    SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.forLanguageTag("es-CL")).format(Date(this))