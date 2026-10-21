package com.example.dsy1105_009v_2026.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dsy1105_009v_2026.util.comoFechaHora
import com.example.dsy1105_009v_2026.viewmodel.SolicitudViewModel

import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.IconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaSolicitudesScreen(
    username: String,
    navController: NavController,
    viewModel: SolicitudViewModel
) {
    // remember(username): crea el Flow una sola vez (no en cada reconocimiento)
    val flujo = remember(username) { viewModel.solicitudesDe(username) }

    // null = todavía cargando desde Room
    val solicitudes by flujo.collectAsState(initial = null)
    val lista = solicitudes

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis solicitudes") },
                actions = {
                    IconButton(onClick = { navController.navigate("qr/$username") }) {
                        Icon(Icons.Default.QrCodeScanner, contentDescription = "Escanear QR")
                    }
                    TextButton(
                        onClick = {
                            navController.navigate("login") {
                                popUpTo(navController.graph.id) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    ) {
                        Text("Cerrar sesión")
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    viewModel.limpiarFormulario()
                    navController.navigate("nueva/$username")
                },
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("Nueva solicitud") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Hola, $username",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(12.dp))

            when {
                // Cargando
                lista == null -> CircularProgressIndicator()

                // Sin datos
                lista.isEmpty() -> Text("Aún no tienes solicitudes. Pulsa «Nueva solicitud».")

                // Con datos
                else -> LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(lista) { solicitud ->
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = solicitud.codigo,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = solicitud.titulo,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold
                                )
                                Text("Categoría: ${solicitud.categoria.etiqueta}")
                                Text("Estado: ${solicitud.estado.etiqueta}")
                                Text("Actualizada: ${solicitud.actualizadaEn.comoFechaHora()}")
                            }
                        }
                    }
                }
            }
        }
    }
}