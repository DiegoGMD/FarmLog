package com.gmdproject.farmlog_project.FrontPage

import android.content.Context
import android.util.Log
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.gmdproject.farmlog_project.DBClasses.AppUser
import com.gmdproject.farmlog_project.FarmLogDatabase
import com.gmdproject.farmlog_project.GlobalVariables
import com.gmdproject.farmlog_project.ui.theme.GreenishWhite
import com.gmdproject.farmlog_project.ui.theme.SourGummy

@Composable
fun LogIn(modifier: Modifier, navController: NavController) {
    var nif by remember { mutableStateOf("") }
    val (nifError, setNifError) = remember { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }
    val (passwordError, setPasswordError) = remember { mutableStateOf(false) }
    val context = LocalContext.current

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
            text = "Inicia Sesión",
            color = Color.Black,
            fontSize = 25.sp,
            textAlign = TextAlign.Center,
            fontFamily = SourGummy
        )

        Spacer(modifier = Modifier.height(10.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)
            ),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = nif,
                    onValueChange = {
                        nif = it
                        setNifError(it.isEmpty())
                    },
                    isError = nifError,
                    label = { Text("NIF") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
                )

                Spacer(modifier = Modifier.height(5.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        setPasswordError(it.isEmpty())
                    },
                    isError = passwordError,
                    label = { Text("Contraseña") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    )
                )

                Spacer(modifier = Modifier.height(5.dp))

                Button(
                    onClick = {
                        val isValid = nif.isNotEmpty() && password.isNotEmpty()
                        setNifError(nif.isEmpty())
                        setPasswordError(password.isEmpty())
                        if (isValid) {
                            val loggedUser = AppUser().getUser(context, nif, password)
                            if (loggedUser != null) {
                                GlobalVariables.userId = loggedUser.id!!
                                navController.navigate("AgriculturalHoldingSelector")
                            } else {
                                Log.e("SignIn", "Login failed: User not found")
                            }
                        } else {
                            Log.e("SignIn", "Invalid credentials")
                        }
                    },
                    modifier = Modifier
                        .width(200.dp)
                        .height(60.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                    shape = MaterialTheme.shapes.large,
                ) {
                    Text(
                        text = "Iniciar Sesión",
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