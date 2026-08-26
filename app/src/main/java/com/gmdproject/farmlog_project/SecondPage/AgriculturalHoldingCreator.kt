package com.gmdproject.farmlog_project.SecondPage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gmdproject.farmlog_project.DBClasses.AgriculturalHolding
import com.gmdproject.farmlog_project.FrontPage.wallpaper
import com.gmdproject.farmlog_project.GlobalVariables
import com.gmdproject.farmlog_project.ui.theme.GreenishWhite
import com.gmdproject.farmlog_project.ui.theme.SourGummy

@Composable
fun AgriculturalHoldingCreator(modifier: Modifier, navController: NavHostController) {
    var agriculturalHolding by remember {
        mutableStateOf(
            AgriculturalHolding(
                agriculturalHoldingId = 0,
                name = "",
                location = "",
                owner = "",
                registrationNumber = ""
            )
        )
    }
    var context = LocalContext.current
    val (nameError, setNameError) = remember { mutableStateOf(false) }
    val (locationError, setLocationError) = remember { mutableStateOf(false) }
    val (ownerError, setOwnerError) = remember { mutableStateOf(false) }
    val (registrationNumberError, setRegistrationNumberError) = remember { mutableStateOf(false) }

    wallpaper()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "FarmLog",
            color = Color.Black,
            fontSize = 50.sp,
            textAlign = TextAlign.Center,
            fontFamily = SourGummy
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Nueva Explotación",
            fontSize = 25.sp,
            textAlign = TextAlign.Center,
            fontFamily = SourGummy
        )
        Spacer(modifier = Modifier.height(10.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)
            ), shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Name Input
                OutlinedTextField(value = agriculturalHolding.name,
                    onValueChange = { newValue ->
                        agriculturalHolding = agriculturalHolding.copy(name = newValue)
                        setNameError(newValue.isEmpty())
                    },
                    isError = nameError,
                    supportingText = {
                        if (nameError) {
                            Text(
                                text = "Nombre es un campo obligatorio", color = Color.Red
                            )
                        } else {
                            Text(
                                text = "Nombre es un campo obligatorio",
                            )
                        }
                    },
                    label = { Text("Nombre") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
                )
                Spacer(modifier = Modifier.height(5.dp))

                // Location Input
                OutlinedTextField(value = agriculturalHolding.location,
                    onValueChange = { newValue ->
                        agriculturalHolding = agriculturalHolding.copy(location = newValue)
                        setLocationError(newValue.isEmpty())
                    },
                    isError = locationError,
                    supportingText = {
                        if (locationError) {
                            Text(
                                text = "Ubicación es un campo obligatorio", color = Color.Red
                            )
                        } else {
                            Text(
                                text = "Ubicación es un campo obligatorio",
                            )
                        }
                    },
                    label = { Text("Ubicación") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
                )
                Spacer(modifier = Modifier.height(5.dp))

                // Owner Input
                OutlinedTextField(value = agriculturalHolding.owner,
                    onValueChange = { newValue ->
                        agriculturalHolding = agriculturalHolding.copy(owner = newValue)
                        setOwnerError(newValue.isEmpty())
                    },
                    isError = ownerError,
                    supportingText = {
                        if (ownerError) {
                            Text(
                                text = "Propietario es un campo obligatorio", color = Color.Red
                            )
                        } else {
                            Text(
                                text = "Propietario es un campo obligatorio"
                            )
                        }
                    },
                    label = { Text("Propietario") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
                )
                Spacer(modifier = Modifier.height(5.dp))

                // Registration Number Input
                OutlinedTextField(value = agriculturalHolding.registrationNumber,
                    onValueChange = { newValue ->
                        agriculturalHolding =
                            agriculturalHolding.copy(registrationNumber = newValue)
                    },
                    isError = registrationNumberError,
                    supportingText = {
                        if (registrationNumberError) {
                            Text(
                                text = "N.Registro Nacional es un campo obligatorio",
                                color = Color.Red
                            )
                        } else {
                            Text(
                                text = "N.Registro Nacional es un campo obligatorio"
                            )
                        }
                    },
                    label = { Text("N.Registro Nacional") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {
                        val isValid =
                            agriculturalHolding.name.isNotEmpty() && agriculturalHolding.location.isNotEmpty() && agriculturalHolding.owner.isNotEmpty() && agriculturalHolding.registrationNumber.isNotEmpty()

                        setNameError(agriculturalHolding.name.isEmpty())
                        setLocationError(agriculturalHolding.location.isEmpty())
                        setOwnerError(agriculturalHolding.owner.isEmpty())
                        setRegistrationNumberError(agriculturalHolding.registrationNumber.isEmpty())

                        if (isValid) {
                            agriculturalHolding.insertAgriculturalHolding(
                                context,
                                GlobalVariables.userId
                            )
                            navController.navigate("AgriculturalHoldingSelector")
                        }
                    },
                    modifier = Modifier
                        .width(200.dp)
                        .height(60.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                    shape = MaterialTheme.shapes.large,
                ) {
                    Text(
                        text = "Guardar",
                        color = GreenishWhite,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = SourGummy
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AgriculturalHoldingCreatorPreview() {
    val navController = rememberNavController()
    AgriculturalHoldingCreator(modifier = Modifier.fillMaxSize(), navController = navController)
}