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
import com.gmdproject.farmlog_project.DBClasses.Plot
import com.gmdproject.farmlog_project.DBClasses.getPlotsByAgriculturalHolding
import com.gmdproject.farmlog_project.GlobalVariables
import com.gmdproject.farmlog_project.ui.theme.LimeGreen
import com.gmdproject.farmlog_project.ui.theme.SourGummy

@Composable
fun plotCardHomePage() {
    var context = LocalContext.current
    var agriculturalHoldingId = GlobalVariables.agriculturalHoldingId

    var plotList by remember { mutableStateOf(getPlotsByAgriculturalHolding(context, agriculturalHoldingId)) }

    var isAddingPlot by remember { mutableStateOf(false) }
    var isErasingPlot by remember { mutableStateOf(false) }
    var isModifyingPlot by remember { mutableStateOf(false) }

    var selectedPlotToModify by remember { mutableStateOf<Plot?>(null) }

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
                        isAddingPlot -> "Nueva Parcela"
                        isModifyingPlot -> "Modificar Parcela"
                        else -> "Información: Parcelas"
                    },
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )

                IconButton(
                    onClick = {
                        plotList = getPlotsByAgriculturalHolding(context, agriculturalHoldingId)
                    },
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.Gray, shape = CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refescar lista",
                        tint = Color.White
                    )
                }

                IconButton(
                    onClick = {
                        isAddingPlot = !isAddingPlot
                    },
                    modifier = Modifier
                        .size(40.dp)
                        .background(LimeGreen, shape = CircleShape)
                ) {
                    Icon(
                        imageVector = if (isAddingPlot) Icons.Default.Close else Icons.Default.Add,
                        contentDescription = if (isAddingPlot) "Cancelar" else "Agregar nueva parcela",
                        tint = Color.White
                    )
                }

                IconButton(
                    onClick = {
                        isErasingPlot = !isErasingPlot
                    },
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = 1.dp)
                        .background(Color.Red, shape = CircleShape)
                ) {
                    Icon(
                        imageVector = if (isErasingPlot) Icons.Default.Close else Icons.Default.Clear,
                        contentDescription = if (isErasingPlot) "Cancelar" else "Agregar nueva parcela",
                        tint = Color.White
                    )
                }
            }

            if (isAddingPlot) {
                addingPlot(
                    context = context,
                    agriculturalHoldingId = agriculturalHoldingId,
                    onSaveClicked = {
                        isAddingPlot = false
                        plotList = getPlotsByAgriculturalHolding(context, agriculturalHoldingId)
                    }
                )
            }

            else if (isModifyingPlot && selectedPlotToModify != null) {
                modifyingPlot(
                    context = context,
                    plotToModify = selectedPlotToModify!!,
                    onSaveClicked = {
                        isModifyingPlot = false
                        selectedPlotToModify = null
                        plotList = getPlotsByAgriculturalHolding(context, agriculturalHoldingId)
                    }
                )
            }

            else {
                plotList.forEach { plot ->
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
                                modifier = Modifier.weight(4f).padding(10.dp)
                            ) {
                                Text(
                                    text = "Id: ${plot.plotId}",
                                    fontFamily = SourGummy,
                                    fontSize = 20.sp,
                                    textAlign = TextAlign.Left,
                                )
                                Text(
                                    text = "Tipo de Cultivo: ${plot.cropType}",
                                    fontFamily = SourGummy,
                                    fontSize = 20.sp,
                                    textAlign = TextAlign.Left,
                                )
                                Text(
                                    text = "Area (m^2): ${plot.area}",
                                    fontFamily = SourGummy,
                                    fontSize = 20.sp,
                                    textAlign = TextAlign.Left,
                                )
                                Text(
                                    text = "Fecha de siembra: ${plot.sowingDate}",
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
                                        if (isErasingPlot) {
                                            plot.erasePlot(context)
                                            plotList = getPlotsByAgriculturalHolding(context, agriculturalHoldingId)
                                        } else {
                                            selectedPlotToModify = plot
                                            isModifyingPlot = true
                                        }
                                    },
                                    modifier = Modifier,
                                    enabled = true
                                ) {
                                    Icon(
                                        imageVector = if (isErasingPlot) Icons.Default.Close else Icons.Default.Settings,
                                        contentDescription = if (isErasingPlot) "Eliminar parcela" else "Modificar parcela",
                                        tint = if (isErasingPlot) Color.Red else Color.Gray
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
fun addingPlot(
    context: Context,
    agriculturalHoldingId: Int,
    onSaveClicked: () -> Unit
) {
    var newPlot by remember { mutableStateOf(
        Plot(
            plotId = 0,
            agriculturalHoldingId = agriculturalHoldingId,
            area = 0.0,
            cropType = "",
            sowingDate = ""
        )
    ) }

    Column {
        Spacer(modifier = Modifier.height(10.dp))

        // Crop Type Input
        OutlinedTextField(
            value = newPlot.cropType,
            onValueChange = { newValue ->
                newPlot = newPlot.copy(cropType = newValue)
            },
            label = { Text("Tipo de Cultivo") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            singleLine = true
        )

        // Area Input
        OutlinedTextField(
            value = newPlot.area.toString(),
            onValueChange = { newValue ->
                newPlot = newPlot.copy(area = newValue.toDouble())
            },
            label = { Text("Área (m^2)") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )

        // Sowing Date Input
        OutlinedTextField(
            value = newPlot.sowingDate,
            onValueChange = { newValue ->
                newPlot = newPlot.copy(sowingDate = newValue)
            },
            label = { Text("Fecha de Siembra") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                if (newPlot.cropType.isNotEmpty() && newPlot.area >= 0) {
                    newPlot.insertNewPlot(context)
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
                text = "Guardar Parcela",
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                fontFamily = SourGummy
            )
        }
    }
}

@Composable
fun modifyingPlot(
    context: Context,
    plotToModify: Plot,
    onSaveClicked: () -> Unit
) {
    var plot by remember { mutableStateOf(plotToModify) }

    Column {
        Spacer(modifier = Modifier.height(10.dp))

        // Crop Type Input
        OutlinedTextField(
            value = plot.cropType,
            onValueChange = { newValue ->
                plot = plot.copy(cropType = newValue)
            },
            label = { Text("Tipo de Cultivo") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            singleLine = true
        )

        // Area Input
        OutlinedTextField(
            value = plot.area.toString(),
            onValueChange = { newValue ->
                plot = plot.copy(area = newValue.toDoubleOrNull() ?: 0.0)
            },
            label = { Text("Área") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )

        // Sowing Date Input
        OutlinedTextField(
            value = plot.sowingDate,
            onValueChange = { newValue ->
                plot = plot.copy(sowingDate = newValue)
            },
            label = { Text("Fecha de Siembra") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                if (plot.cropType.isNotEmpty() && plot.area >= 0) {
                    plot.updatePlot(context)
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
                text = "Modificar Parcela",
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                fontFamily = SourGummy
            )
        }
    }
}