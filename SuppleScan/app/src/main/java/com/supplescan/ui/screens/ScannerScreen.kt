package com.supplescan.ui.screens

import android.Manifest
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.*
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.supplescan.ui.theme.SuppleScanGreen
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ScannerScreen(
    onBarcodeScanned: (String) -> Unit
) {
    val cameraPermission = rememberPermissionState(Manifest.permission.CAMERA)

    if (cameraPermission.status.isGranted) {
        CameraPreview(onBarcodeScanned = onBarcodeScanned)
    } else {
        PermissionRequest(
            onRequest = { cameraPermission.launchPermissionRequest() },
            isPermanentlyDenied = cameraPermission.status.shouldShowRationale.not()
                    && !cameraPermission.status.isGranted
        )
    }
}

@Composable
private fun CameraPreview(
    onBarcodeScanned: (String) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var scanned by remember { mutableStateOf(false) }
    var scanningActive by remember { mutableStateOf(true) }

    val executor: ExecutorService = remember { Executors.newSingleThreadExecutor() }
    val barcodeScanner = remember { BarcodeScanning.getClient() }

    DisposableEffect(Unit) {
        onDispose {
            executor.shutdown()
            barcodeScanner.close()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        // Camera preview
        AndroidView(
            factory = { ctx ->
                val previewView = PreviewView(ctx)
                val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
                cameraProviderFuture.addListener({
                    val cameraProvider = cameraProviderFuture.get()
                    val preview = Preview.Builder().build()
                        .also { it.surfaceProvider = previewView.surfaceProvider }

                    val imageAnalysis = ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()
                        .also { analysis ->
                            analysis.setAnalyzer(executor) { imageProxy ->
                                if (!scanningActive) {
                                    imageProxy.close()
                                    return@setAnalyzer
                                }
                                processImageForBarcode(
                                    imageProxy = imageProxy,
                                    barcodeScanner = barcodeScanner,
                                    onBarcodeDetected = { barcode ->
                                        if (!scanned) {
                                            scanned = true
                                            scanningActive = false
                                            onBarcodeScanned(barcode)
                                        }
                                    }
                                )
                            }
                        }

                    try {
                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(
                            lifecycleOwner,
                            CameraSelector.DEFAULT_BACK_CAMERA,
                            preview,
                            imageAnalysis
                        )
                    } catch (_: Exception) {}
                }, ContextCompat.getMainExecutor(ctx))
                previewView
            },
            modifier = Modifier.fillMaxSize()
        )

        // Overlay UI
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.6f))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Scansiona il barcode dell'integratore",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(Modifier.weight(1f))

            // Scan frame
            Box(
                modifier = Modifier
                    .size(260.dp, 160.dp)
                    .border(
                        width = 2.dp,
                        color = SuppleScanGreen,
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                // Corner accents
                ScanCorner(Modifier.align(Alignment.TopStart))
                ScanCorner(Modifier.align(Alignment.TopEnd).then(Modifier.scale(scaleX = -1f, scaleY = 1f)))
                ScanCorner(Modifier.align(Alignment.BottomStart).then(Modifier.scale(scaleX = 1f, scaleY = -1f)))
                ScanCorner(Modifier.align(Alignment.BottomEnd).then(Modifier.scale(scaleX = -1f, scaleY = -1f)))
            }

            Spacer(Modifier.height(20.dp))
            Text(
                text = "Inquadra il codice a barre o QR",
                color = Color.White.copy(alpha = 0.85f),
                fontSize = 14.sp
            )

            Spacer(Modifier.weight(1f))

            // Bottom hint
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.6f))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "💡 Puoi anche inserire il barcode manualmente",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun ScanCorner(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(24.dp)
            .clip(RoundedCornerShape(topStart = 8.dp))
            .background(SuppleScanGreen)
    )
}

private fun Modifier.scale(scaleX: Float = 1f, scaleY: Float = 1f): Modifier =
    this.then(Modifier.graphicsLayer(scaleX = scaleX, scaleY = scaleY))

@androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
private fun processImageForBarcode(
    imageProxy: ImageProxy,
    barcodeScanner: com.google.mlkit.vision.barcode.BarcodeScanner,
    onBarcodeDetected: (String) -> Unit
) {
    val mediaImage = imageProxy.image
    if (mediaImage != null) {
        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
        barcodeScanner.process(image)
            .addOnSuccessListener { barcodes ->
                for (barcode in barcodes) {
                    val value = barcode.rawValue
                    if (value != null && barcode.format in listOf(
                            Barcode.FORMAT_EAN_13,
                            Barcode.FORMAT_EAN_8,
                            Barcode.FORMAT_UPC_A,
                            Barcode.FORMAT_UPC_E,
                            Barcode.FORMAT_QR_CODE,
                            Barcode.FORMAT_DATA_MATRIX
                        )
                    ) {
                        onBarcodeDetected(value)
                        break
                    }
                }
            }
            .addOnCompleteListener { imageProxy.close() }
    } else {
        imageProxy.close()
    }
}

@Composable
private fun PermissionRequest(
    onRequest: () -> Unit,
    isPermanentlyDenied: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.CameraAlt,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = SuppleScanGreen
        )
        Spacer(Modifier.height(24.dp))
        Text(
            text = "Accesso alla fotocamera necessario",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = if (isPermanentlyDenied)
                "La fotocamera è stata negata in modo permanente. Abilitala nelle Impostazioni dell'app."
            else
                "SuppleScan ha bisogno della fotocamera per scansionare i barcode degli integratori.",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(24.dp))
        if (!isPermanentlyDenied) {
            Button(
                onClick = onRequest,
                colors = ButtonDefaults.buttonColors(containerColor = SuppleScanGreen)
            ) {
                Text("Concedi accesso")
            }
        } else {
            OutlinedButton(onClick = onRequest) {
                Text("Apri Impostazioni")
            }
        }
    }
}
