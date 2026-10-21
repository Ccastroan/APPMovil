package com.example.dsy1105_009v_2026.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.dsy1105_009v_2026.data.SolicitudDatabase
import com.example.dsy1105_009v_2026.model.Categoria
import com.example.dsy1105_009v_2026.model.SolicitudEntity
import com.example.dsy1105_009v_2026.model.SolicitudErrores
import com.example.dsy1105_009v_2026.model.SolicitudUiState
import com.example.dsy1105_009v_2026.repository.SolicitudRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SolicitudViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = SolicitudRepository(
        SolicitudDatabase.getDatabase(app).solicitudDao()
    )

    // Estado interno mutable
    private val _estado = MutableStateFlow(SolicitudUiState())

    // Estado expuesto para el UI
    val estado: StateFlow<SolicitudUiState> = _estado

    // Actualiza el campo título y limpia el error
    fun onTituloChange(valor: String) {
        _estado.update {
            it.copy(
                titulo = valor,
                errores = it.errores.copy(titulo = null)
            )
        }
    }

    // Actualiza el campo descripción
    fun onDescripcionChange(valor: String) {
        _estado.update {
            it.copy(
                descripcion = valor,
                errores = it.errores.copy(descripcion = null)
            )
        }
    }

    // Actualiza la categoría elegida
    fun onCategoriaChange(valor: Categoria) {
        _estado.update {
            it.copy(
                categoria = valor,
                errores = it.errores.copy(categoria = null)
            )
        }
    }

    // Actualiza el campo servicio
    fun onServicioChange(valor: String) {
        _estado.update {
            it.copy(
                servicio = valor,
                errores = it.errores.copy(servicio = null)
            )
        }
    }

    // Validación global del formulario
    fun validarFormulario(): Boolean {
        val estadoActual = _estado.value

        val errores = SolicitudErrores(
            titulo = if (estadoActual.titulo.isBlank())
                "Campo obligatorio"
            else if (estadoActual.titulo.trim().length < 5)
                "Debe tener al menos 5 caracteres"
            else if (estadoActual.titulo.trim().length > 60)
                "Máximo 60 caracteres"
            else null,

            descripcion = if (estadoActual.descripcion.isBlank())
                "Campo obligatorio"
            else if (estadoActual.descripcion.trim().length < 20)
                "Debe tener al menos 20 caracteres"
            else null,

            categoria = if (estadoActual.categoria == null)
                "Selecciona una categoría"
            else null,

            servicio = if (estadoActual.servicio.isBlank())
                "Campo obligatorio"
            else null
        )

        val hayErrores = listOfNotNull(
            errores.titulo,
            errores.descripcion,
            errores.categoria,
            errores.servicio
        ).isNotEmpty()

        _estado.update {
            it.copy(errores = errores)
        }

        return !hayErrores
    }

    // Guarda la solicitud en Room y luego ejecuta
    fun guardar(cliente: String, onListo: () -> Unit) {
        val actual = _estado.value
        val categoria = actual.categoria ?: return
        if (actual.guardando) return

        viewModelScope.launch {
            _estado.update { it.copy(guardando = true, errorGuardado = null) }

            val nueva = try {
                repo.guardar(
                    SolicitudEntity(
                        cliente = cliente,
                        titulo = actual.titulo.trim(),
                        descripcion = actual.descripcion.trim(),
                        categoria = categoria,
                        servicio = actual.servicio.trim()
                    )
                )
            } catch (e: Exception) {
                null
            }

            if (nueva != null) {
                // Formulario vacío otra vez; la solicitud queda para el resumen
                _estado.update { SolicitudUiState(ultimaSolicitud = nueva) }
                onListo()
            } else {
                _estado.update {
                    it.copy(
                        guardando = false,
                        errorGuardado = "No se pudo guardar la solicitud. Intenta de nuevo."
                    )
                }
            }
        }
    }

    // Deja el formulario vacío
    fun limpiarFormulario() {
        _estado.update { SolicitudUiState() }
    }

    // Lista de solicitudes de un cliente ,se actualiza sola cuando cambia Room,
    fun solicitudesDe(cliente: String): Flow<List<SolicitudEntity>> =
        repo.observarPorCliente(cliente)
}