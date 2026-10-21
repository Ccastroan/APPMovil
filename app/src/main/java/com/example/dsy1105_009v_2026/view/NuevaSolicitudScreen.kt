package com.example.dsy1105_009v_2026.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dsy1105_009v_2026.model.Categoria
import com.example.dsy1105_009v_2026.viewmodel.SolicitudViewModel

import androidx.compose.ui.platform.LocalContext
import com.example.dsy1105_009v_2026.util.Notificador

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevaSolicitudScreen(
    username: String,
    navController: NavController,
    viewModel: SolicitudViewModel
) {
    val estado by viewModel.estado.collectAsState()

    val context = LocalContext.current

    val formularioCompleto by remember {
        derivedStateOf {
            estado.titulo.isNotBlank() &&
                    estado.descripcion.isNotBlank() &&
                    estado.categoria != null &&
                    estado.servicio.isNotBlank()
        }
    }

    val colorBoton by animateColorAsState(
        targetValue = if (formularioCompleto)
            MaterialTheme.colorScheme.primary
        else
            MaterialTheme.colorScheme.outline,
        label = "colorBoton"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nueva solicitud") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            //título
            OutlinedTextField(
                value = estado.titulo,
                onValueChange = viewModel::onTituloChange,
                label = { Text("Título") },
                leadingIcon = { Icon(Icons.Default.Title, contentDescription = null) },
                trailingIcon = {
                    if (estado.errores.titulo != null) {
                        Icon(
                            Icons.Default.Error,
                            contentDescription = "Error",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                },
                singleLine = true,
                isError = estado.errores.titulo != null,
                supportingText = {
                    estado.errores.titulo?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            // descripción
            OutlinedTextField(
                value = estado.descripcion,
                onValueChange = viewModel::onDescripcionChange,
                label = { Text("Descripción de la necesidad") },
                leadingIcon = { Icon(Icons.Default.Description, contentDescription = null) },
                trailingIcon = {
                    if (estado.errores.descripcion != null) {
                        Icon(
                            Icons.Default.Error,
                            contentDescription = "Error",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                },
                minLines = 3,
                isError = estado.errores.descripcion != null,
                supportingText = {
                    estado.errores.descripcion?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            // Selección de categoría
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Category, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "Categoría",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(Categoria.entries) { categoria ->
                    FilterChip(
                        selected = estado.categoria == categoria,
                        onClick = { viewModel.onCategoriaChange(categoria) },
                        label = { Text(categoria.etiqueta) }
                    )
                }
            }
            AnimatedVisibility(visible = estado.errores.categoria != null) {
                Text(
                    text = estado.errores.categoria ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            // Campo servicio
            OutlinedTextField(
                value = estado.servicio,
                onValueChange = viewModel::onServicioChange,
                label = { Text("Servicio o activo relacionado") },
                placeholder = { Text("Ej: sitio web, hosting, correo") },
                leadingIcon = { Icon(Icons.Default.Language, contentDescription = null) },
                trailingIcon = {
                    if (estado.errores.servicio != null) {
                        Icon(
                            Icons.Default.Error,
                            contentDescription = "Error",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                },
                singleLine = true,
                isError = estado.errores.servicio != null,
                supportingText = {
                    estado.errores.servicio?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            // Botón: enviar (primero valida, después guarda para navegar)
            Button(
                onClick = {
                    if (viewModel.validarFormulario()) {
                        viewModel.guardar(username) {
                            val codigo = viewModel.estado.value.ultimaSolicitud?.codigo ?: ""
                            Notificador.mostrar(
                                context,
                                "Solicitud enviada",
                                "Tu código de seguimiento es $codigo"
                            )
                            navController.navigate("resumen/$username")
                        }
                    }
                },
                enabled = !estado.guardando,
                colors = ButtonDefaults.buttonColors(containerColor = colorBoton),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (estado.guardando) "Guardando..." else "Enviar solicitud")
            }

            // Error al guardar en la base de datos
            estado.errorGuardado?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}