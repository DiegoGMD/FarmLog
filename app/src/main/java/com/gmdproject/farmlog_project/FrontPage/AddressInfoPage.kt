package com.gmdproject.farmlog_project.FrontPage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gmdproject.farmlog_project.DBClasses.AppUser
import com.gmdproject.farmlog_project.ui.theme.GreenishWhite
import com.gmdproject.farmlog_project.ui.theme.SourGummy

@Composable
fun AddressInfoPage(
    appUser: AppUser,
    onDataUpdated: (AppUser) -> Unit,
    onPreviousPage: () -> Unit,
    onNextPage: () -> Unit
) {
    Spacer(modifier = Modifier.height(10.dp))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)
        ), shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Phone Input
            OutlinedTextField(
                value = appUser.phone,
                onValueChange = {
                    onDataUpdated(appUser.copy(phone = it))
                },
                label = { Text("Teléfono") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
                )
            )
            Spacer(modifier = Modifier.height(5.dp))

            // Address Input
            OutlinedTextField(
                value = appUser.address,
                onValueChange = {
                    onDataUpdated(appUser.copy(address = it))
                },
                label = { Text("Dirección") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                )
            )
            Spacer(modifier = Modifier.height(5.dp))

            // City Input
            OutlinedTextField(
                value = appUser.city,
                onValueChange = {
                    onDataUpdated(appUser.copy(city = it))
                },
                label = { Text("Ciudad") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                )
            )
            Spacer(modifier = Modifier.height(5.dp))

            // Province Input
            OutlinedTextField(
                value = appUser.province,
                onValueChange = {
                    onDataUpdated(appUser.copy(province = it))
                },
                label = { Text("Provincia") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                )
            )
            Spacer(modifier = Modifier.height(5.dp))

            // Postal Code Input
            OutlinedTextField(
                value = appUser.postalCode,
                onValueChange = {
                    onDataUpdated(appUser.copy(postalCode = it))
                },
                label = { Text("Código Postal") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                )
            )
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = onPreviousPage,
                    modifier = Modifier
                        .width(100.dp)
                        .height(60.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Gray
                    ),
                    shape = MaterialTheme.shapes.large,
                ) {
                    Text(
                        text = "Atrás",
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = SourGummy
                    )
                }

                Button(
                    onClick = { onNextPage() },
                    modifier = Modifier
                        .width(110.dp)
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
}