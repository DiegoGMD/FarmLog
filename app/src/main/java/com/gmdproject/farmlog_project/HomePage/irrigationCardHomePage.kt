package com.gmdproject.farmlog_project.HomePage

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gmdproject.farmlog_project.DBClasses.Irrigation
import com.gmdproject.farmlog_project.DBClasses.getIrrigationsByAgriculturalHolding
import com.gmdproject.farmlog_project.GlobalVariables
import com.gmdproject.farmlog_project.ui.theme.LimeGreen
import com.gmdproject.farmlog_project.ui.theme.SourGummy

@Composable
fun irrigationCardHomePage() {
    var context = LocalContext.current
    var agriculturalHoldingId = GlobalVariables.agriculturalHoldingId

    var irrigationList by remember {
        mutableStateOf(
            getIrrigationsByAgriculturalHolding(
                context,
                agriculturalHoldingId
            )
        )
    }

    var isAddingIrrigation by remember { mutableStateOf(false) }
    var isErasingIrrigation by remember { mutableStateOf(false) }
    var isModifyingIrrigation by remember { mutableStateOf(false) }

    var selectedIrrigationToModify by remember { mutableStateOf<Irrigation?>(null) }

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = when {
                        isAddingIrrigation -> "Nuevo Riego"
                        isModifyingIrrigation -> "Modificar Riego"
                        else -> "Información: Riegos"
                    }, fontSize = 20.sp, fontWeight = FontWeight.SemiBold
                )

                IconButton(
                    onClick = {
                        irrigationList =
                            getIrrigationsByAgriculturalHolding(context, agriculturalHoldingId)
                    }, modifier = Modifier
                        .size(40.dp)
                        .background(Color.Gray, shape = CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refrescar lista",
                        tint = Color.White
                    )
                }

                IconButton(
                    onClick = {
                        isAddingIrrigation = !isAddingIrrigation
                    }, modifier = Modifier
                        .size(40.dp)
                        .background(LimeGreen, shape = CircleShape)
                ) {
                    Icon(
                        imageVector = if (isAddingIrrigation) Icons.Default.Close else Icons.Default.Add,
                        contentDescription = if (isAddingIrrigation) "Cancelar" else "Agregar nuevo riego",
                        tint = Color.White
                    )
                }

                IconButton(
                    onClick = {
                        isErasingIrrigation = !isErasingIrrigation
                    },
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = 1.dp)
                        .background(Color.Red, shape = CircleShape)
                ) {
                    Icon(
                        imageVector = if (isErasingIrrigation) Icons.Default.Close else Icons.Default.Clear,
                        contentDescription = if (isErasingIrrigation) "Cancelar" else "Eliminar riego",
                        tint = Color.White
                    )
                }
            }

            if (isAddingIrrigation) {
                addingIrrigation(
                    context = context,
                    agriculturalHoldingId = agriculturalHoldingId,
                    onSaveClicked = {
                        isAddingIrrigation = false
                        irrigationList =
                            getIrrigationsByAgriculturalHolding(context, agriculturalHoldingId)
                    }
                )
            } else if (isModifyingIrrigation && selectedIrrigationToModify != null) {
                modifyingIrrigation(
                    context = context,
                    irrigationToModify = selectedIrrigationToModify!!,
                    onSaveClicked = {
                        isModifyingIrrigation = false
                        selectedIrrigationToModify = null
                        irrigationList =
                            getIrrigationsByAgriculturalHolding(context, agriculturalHoldingId)
                    }
                )
            } else {
                irrigationList.forEach { irrigation ->
                    ElevatedCard(
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable {}
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(4f)
                                    .padding(10.dp)
                            ) {
                                Text(
                                    text = "Fecha: ${irrigation.irrigationDate}",
                                    fontFamily = SourGummy,
                                    fontSize = 20.sp,
                                    textAlign = TextAlign.Left,
                                )
                                Text(
                                    text = "Método: ${irrigation.irrigationMethod}",
                                    fontFamily = SourGummy,
                                    fontSize = 20.sp,
                                    textAlign = TextAlign.Left,
                                )
                                Text(
                                    text = "Volumen de Agua: ${irrigation.waterVolume} L",
                                    fontFamily = SourGummy,
                                    fontSize = 20.sp,
                                    textAlign = TextAlign.Left,
                                )
                            }
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.End
                            ) {
                                IconButton(
                                    onClick = {
                                        if (isErasingIrrigation) {
                                            irrigation.eraseIrrigation(context)
                                            irrigationList = getIrrigationsByAgriculturalHolding(
                                                context,
                                                agriculturalHoldingId
                                            )
                                        } else {
                                            selectedIrrigationToModify = irrigation
                                            isModifyingIrrigation = true
                                        }
                                    },
                                    modifier = Modifier,
                                    enabled = true
                                ) {
                                    Icon(
                                        imageVector = if (isErasingIrrigation) Icons.Default.Close else Icons.Default.Settings,
                                        contentDescription = if (isErasingIrrigation) "Eliminar riego" else "Modificar riego",
                                        tint = if (isErasingIrrigation) Color.Red else Color.Gray
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun addingIrrigation(
    context: Context,
    agriculturalHoldingId: Int,
    onSaveClicked: () -> Unit
) {
    var plotId by remember { mutableStateOf(0) }

    var newIrrigation by remember {
        mutableStateOf(
            Irrigation(
                irrigationId = 0,
                plotId = plotId,
                irrigationDate = "",
                irrigationMethod = "",
                waterVolume = 0.0
            )
        )
    }

    Column {
        Spacer(modifier = Modifier.height(10.dp))

        // Plot ID (in a real app, this would be a dropdown/spinner)
        OutlinedTextField(
            value = plotId.toString(),
            onValueChange = { newValue ->
                plotId = newValue.toIntOrNull() ?: 0
            },
            label = { Text("ID de Parcela") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )

        // Irrigation Date Input
        OutlinedTextField(
            value = newIrrigation.irrigationDate,
            onValueChange = { newValue ->
                newIrrigation = newIrrigation.copy(irrigationDate = newValue)
            },
            label = { Text("Fecha de Riego") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            singleLine = true
        )

        // Irrigation Method Input
        OutlinedTextField(
            value = newIrrigation.irrigationMethod,
            onValueChange = { newValue ->
                newIrrigation = newIrrigation.copy(irrigationMethod = newValue)
            },
            label = { Text("Método de Riego") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            singleLine = true
        )

        // Water Volume Input
        OutlinedTextField(
            value = newIrrigation.waterVolume.toString(),
            onValueChange = { newValue ->
                newIrrigation = newIrrigation.copy(waterVolume = newValue.toDoubleOrNull() ?: 0.0)
            },
            label = { Text("Volumen de Agua (L)") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                if (newIrrigation.irrigationDate.isNotEmpty() &&
                    newIrrigation.irrigationMethod.isNotEmpty() &&
                    newIrrigation.waterVolume > 0 &&
                    plotId > 0) {

                    newIrrigation = newIrrigation.copy(plotId = plotId)
                    newIrrigation.insertNewIrrigation(context)
                    onSaveClicked()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50)
            ),
            shape = MaterialTheme.shapes.large
        ) {
            Text(
                text = "Guardar Riego",
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                fontFamily = SourGummy
            )
        }
    }
}

@Composable
fun modifyingIrrigation(
    context: Context,
    irrigationToModify: Irrigation,
    onSaveClicked: () -> Unit
) {
    var irrigation by remember { mutableStateOf(irrigationToModify) }

    Column {
        Spacer(modifier = Modifier.height(10.dp))

        // Plot ID Input
        OutlinedTextField(
            value = irrigation.plotId.toString(),
            onValueChange = { newValue ->
                irrigation = irrigation.copy(plotId = newValue.toIntOrNull() ?: 0)
            },
            label = { Text("ID de Parcela") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )

        // Irrigation Date Input
        OutlinedTextField(
            value = irrigation.irrigationDate,
            onValueChange = { newValue ->
                irrigation = irrigation.copy(irrigationDate = newValue)
            },
            label = { Text("Fecha de Riego") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            singleLine = true
        )

        // Irrigation Method Input
        OutlinedTextField(
            value = irrigation.irrigationMethod,
            onValueChange = { newValue ->
                irrigation = irrigation.copy(irrigationMethod = newValue)
            },
            label = { Text("Método de Riego") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            singleLine = true
        )

        // Water Volume Input
        OutlinedTextField(
            value = irrigation.waterVolume.toString(),
            onValueChange = { newValue ->
                irrigation = irrigation.copy(waterVolume = newValue.toDoubleOrNull() ?: 0.0)
            },
            label = { Text("Volumen de Agua (L)") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                if (irrigation.irrigationDate.isNotEmpty() &&
                    irrigation.irrigationMethod.isNotEmpty() &&
                    irrigation.waterVolume > 0 &&
                    irrigation.plotId > 0) {

                    irrigation.updateIrrigation(context)
                    onSaveClicked()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50)
            ),
            shape = MaterialTheme.shapes.large
        ) {
            Text(
                text = "Modificar Riego",
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                fontFamily = SourGummy
            )
        }
    }
}