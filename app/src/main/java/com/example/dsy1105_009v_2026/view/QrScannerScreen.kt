package com.example.dsy1105_009v_2026.view

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween

import androidx.navigation.NavController
import com.example.dsy1105_009v_2026.util.QrScanner
import com.example.dsy1105_009v_2026.util.comoFechaHora
import com.example.dsy1105_009v_2026.viewmodel.QrViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QrScannerScreen(
    username: String,
    navController: NavController,
    viewModel: QrViewModel,
    hasCameraPermission: Boolean,
    onRequestPermission: () -> Unit
) {
    val qrResult by viewModel.qrResult.observeAsState()
    val resultado = qrResult
    val context = LocalContext.current
    var isScanning by remember { mutableStateOf(true) }

    val transicion = rememberInfiniteTransition(label = "marco")
    val alfaMarco by transicion.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(900),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alfaMarco"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Escanear QR") },
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (!hasCameraPermission) {
                // Escenario 1: falta el permiso de cámara
                Text(
                    "Permiso de cámara requerido",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    "Para escanear códigos QR, necesitamos acceso a la cámara",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(24.dp))
                Button(onClick = onRequestPermission) {
                    Text("Conceder permiso de cámara")
                }
            } else if (resultado == null && isScanning) {
                // Escenario 2: cámara activa.
                Text(
                    "Escanea el QR de una solicitud",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                ) {
                    QrScanner(
                        onQrCodeScanned = { qrContent ->
                            // Procesar el QR detectado
                            viewModel.onQrDetected(qrContent, username)
                            isScanning = false
                            Toast.makeText(context, "QR detectado!", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.fillMaxSize()
                    )

                    // para ayudar al escaneo
                    Surface(
                        modifier = Modifier
                            .size(250.dp)
                            .align(Alignment.Center),
                        color = Color.Transparent,
                        shape = MaterialTheme.shapes.medium,
                        border = BorderStroke(
                            3.dp,
                            MaterialTheme.colorScheme.primary.copy(alpha = alfaMarco)
                        )
                    ) {}
                }

                Spacer(Modifier.height(16.dp))
                Text(
                    "Enfoca el código QR en el marco central",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else if (resultado == null) {
                // Escenario 3: ya se leyó el QR y se está buscando.
                CircularProgressIndicator()
                Spacer(Modifier.height(16.dp))
                Text("Buscando solicitud...")
            } else {
                // Escenario 4: resultado de la búsqueda
                val solicitud = resultado.solicitud

                if (solicitud != null) {
                    Text(
                        "Solicitud encontrada",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = solicitud.codigo,
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = solicitud.titulo,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Text("Categoría: ${solicitud.categoria.etiqueta}")
                            Text("Servicio: ${solicitud.servicio}")
                            Text("Estado: ${solicitud.estado.etiqueta}")
                            Text("Creada: ${solicitud.creadaEn.comoFechaHora()}")
                            Text("Última actualización: ${solicitud.actualizadaEn.comoFechaHora()}")
                        }
                    }
                } else {
                    Text(
                        "No encontramos la solicitud",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Text(
                            "Contenido del QR: ${resultado.content}",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    Text(
                        "Solo puedes consultar tus propias solicitudes (formato SOL-0001).",
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = {
                        viewModel.clearResult()
                        isScanning = true
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Escanear otro código QR")
                }
            }
        }
    }
}