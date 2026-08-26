package com.gmdproject.farmlog_project.FrontPage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gmdproject.farmlog_project.DBClasses.AppUser
import com.gmdproject.farmlog_project.ui.theme.GreenishWhite
import com.gmdproject.farmlog_project.ui.theme.SourGummy

@Composable
fun PersonalInfoPage(
    appUser: AppUser, onDataUpdated: (AppUser) -> Unit, onNextPage: () -> Unit
) {
    val (nifError, setNifError) = remember { mutableStateOf<String?>(null) }
    val (firstNameError, setFirstNameError) = remember { mutableStateOf(false) }
    val (surnamesError, setSurnamesError) = remember { mutableStateOf(false) }
    val (passwordError, setPasswordError) = remember { mutableStateOf(false) }
    val (emailError, setEmailError) = remember { mutableStateOf<String?>(null) }

    // NIF Validation Function
    fun validateNIF(nif: String): Boolean {
        val cleanNif = nif.trim().uppercase()

        if (!cleanNif.matches(Regex("^\\d{8}[A-Z]$"))) {
            return false
        }

        val digits = cleanNif.substring(0, 8)
        val controlLetter = cleanNif[8]
        val remainder = digits.toInt() % 23

        val validControlLetters = "TRWAGMYFPDXBNJZSQVHLCKE"
        return controlLetter == validControlLetters[remainder]
    }

    // Email Validation
    fun validateEmail(email: String): Boolean {
        val gmailRegex = Regex("^[a-zA-Z0-9._%+-]+@gmail\\.com$")
        return gmailRegex.matches(email)
    }

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
            // NIF Input with Enhanced Validation
            OutlinedTextField(
                value = appUser.nif,
                onValueChange = {
                    onDataUpdated(appUser.copy(nif = it))
                    // Validate NIF as user types
                    if (it.isNotEmpty()) {
                        setNifError(
                            if (!validateNIF(it))
                                "NIF inválido. Debe contener 8 dígitos y una letra de control correcta"
                            else null
                        )
                    } else {
                        setNifError("NIF es un campo obligatorio")
                    }
                },
                isError = nifError != null,
                supportingText = {
                    nifError?.let {
                        Text(
                            text = it,
                            color = Color.Red
                        )
                    }
                },
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

            // First Name Input
            OutlinedTextField(
                value = appUser.firstName,
                onValueChange = {
                    onDataUpdated(appUser.copy(firstName = it))
                    setFirstNameError(it.isEmpty())
                },
                isError = firstNameError,
                supportingText = {
                    if (firstNameError) {
                        Text(
                            text = "Nombre es un campo obligatorio",
                            color = Color.Red
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

            // Surnames Input
            OutlinedTextField(
                value = appUser.surnames,
                onValueChange = {
                    onDataUpdated(appUser.copy(surnames = it))
                    setSurnamesError(it.isEmpty())
                },
                isError = surnamesError,
                supportingText = {
                    if (surnamesError) {
                        Text(
                            text = "Apellido es un campo obligatorio",
                            color = Color.Red
                        )
                    }
                },
                label = { Text("Apellido") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                )
            )
            Spacer(modifier = Modifier.height(5.dp))

            // Password Input
            OutlinedTextField(
                value = appUser.password,
                onValueChange = {
                    onDataUpdated(appUser.copy(password = it))
                    setPasswordError(it.isEmpty())
                },
                isError = passwordError,
                supportingText = {
                    if (passwordError) {
                        Text(
                            text = "Contraseña es un campo obligatorio",
                            color = Color.Red
                        )
                    }
                },
                label = { Text("Contraseña") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                )
            )
            Spacer(modifier = Modifier.height(10.dp))

            // Email Validation
            OutlinedTextField(
                value = appUser.email,
                onValueChange = {
                    onDataUpdated(appUser.copy(email = it))
                    // Validate email as user types
                    if (it.isNotEmpty()) {
                        setEmailError(
                            if (!validateEmail(it))
                                "Por favor, use una cuenta de Gmail válida"
                            else null
                        )
                    } else {
                        setEmailError(null)
                    }
                },
                isError = emailError != null,
                supportingText = {
                    emailError?.let {
                        Text(
                            text = it,
                            color = Color.Red
                        )
                    }
                },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done
                )
            )
            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    val isNifValid = validateNIF(appUser.nif)
                    val isEmailValid = validateEmail(appUser.email)

                    setNifError(
                        if (!isNifValid)
                            "NIF inválido. Debe contener 8 dígitos y una letra de control correcta"
                        else null
                    )
                    setFirstNameError(appUser.firstName.isEmpty())
                    setSurnamesError(appUser.surnames.isEmpty())
                    setPasswordError(appUser.password.isEmpty())
                    setEmailError(
                        if (!isEmailValid)
                            "Por favor, use una cuenta de Gmail válida"
                        else null
                    )

                    val isValid = isNifValid &&
                            appUser.firstName.isNotEmpty() &&
                            appUser.surnames.isNotEmpty() &&
                            appUser.password.isNotEmpty() &&
                            isEmailValid

                    if (isValid) {
                        onNextPage()
                    }
                },
                modifier = Modifier
                    .width(200.dp)
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                shape = MaterialTheme.shapes.large,
            ) {
                Text(
                    text = "Siguiente",
                    color = GreenishWhite,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = SourGummy
                )
            }
        }
    }
}