package com.example.dsy1105_009v_2026.model

enum class Categoria(val etiqueta: String) {
    DESARROLLO_WEB("Desarrollo web"),
    MANTENIMIENTO_WEB("Mantenimiento web"),
    INCIDENTE_TECNICO("Incidente técnico"),
    HOSTING("Hosting"),
    SOPORTE_CORREO("Soporte de correo"),
    MARKETING_DIGITAL("Marketing digital"),
    SOLICITUD_CAMBIO("Solicitud de cambio"),
    CONSULTA("Consulta"),
    ACCESO_CONFIGURACION("Acceso y configuración"),
    OTRO("Otro")
}

enum class Prioridad(val etiqueta: String) {
    BAJA("Baja"),
    MEDIA("Media"),
    ALTA("Alta"),
    URGENTE("Urgente")
}

enum class EstadoSolicitud(val etiqueta: String) {
    RECIBIDA("Recibida"),
    EN_REVISION("En revisión"),
    PENDIENTE_INFORMACION("Pendiente de información"),
    ASIGNADA("Asignada"),
    EN_PROCESO("En proceso"),
    RESUELTA("Resuelta"),
    CERRADA("Cerrada"),
    CANCELADA("Cancelada")
}