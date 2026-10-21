package com.example.dsy1105_009v_2026.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dsy1105_009v_2026.util.comoFechaHora
import com.example.dsy1105_009v_2026.viewmodel.SolicitudViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResumenSolicitudScreen(
    username: String,
    navController: NavController,
    viewModel: SolicitudViewModel
) {
    val estado by viewModel.estado.collectAsState()
    val solicitud = estado.ultimaSolicitud

    // Al abrir la pantalla pasa a true y dispara la animación de entrada
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Solicitud enviada") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (solicitud == null) {
                Text("No hay una solicitud para mostrar")
            } else {
                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn(animationSpec = tween(700)) +
                            slideInVertically(animationSpec = tween(700)) { alto -> alto / 3 }
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(
                            text = "Tu código de seguimiento",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = solicitud.codigo,
                            style = MaterialTheme.typography.displaySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text("Título: ${solicitud.titulo}")
                        Text("Categoría: ${solicitud.categoria.etiqueta}")
                        Text("Servicio: ${solicitud.servicio}")
                        Text("Estado: ${solicitud.estado.etiqueta}")
                        Text("Creada: ${solicitud.creadaEn.comoFechaHora()}")
                    }
                }
            }

            Button(
                onClick = {
                    navController.navigate("solicitudes/$username") {
                        popUpTo("solicitudes/{username}") { inclusive = true }
                        launchSingleTop = true
                    }
                }
            ) {
                Text("Ver mis solicitudes")
            }
        }
    }
}