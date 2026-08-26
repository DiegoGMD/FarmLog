package com.gmdproject.farmlog_project.FrontPage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gmdproject.farmlog_project.DBClasses.AppUser
import com.gmdproject.farmlog_project.HomePage.InfoRow
import com.gmdproject.farmlog_project.ui.theme.GreenishWhite
import com.gmdproject.farmlog_project.ui.theme.SourGummy

@Composable
fun UserInfoPage(
    appUser: AppUser, onNextPage: () -> Unit
) {
    Spacer(modifier = Modifier.height(10.dp))
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Personal Information Section
            Text(
                text = "Información Personal",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            InfoRow("NIF", appUser.nif)
            InfoRow("Nombre", "${appUser.firstName} ${appUser.surnames}")
            InfoRow("Contraseña", appUser.password)
            InfoRow("Email", appUser.email)

            Spacer(modifier = Modifier.height(16.dp))

            // Contact Information Section
            Text(
                text = "Información de Contacto",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            InfoRow("Dirección", appUser.address)
            InfoRow("Ciudad", appUser.city)
            InfoRow("Provincia", appUser.province)
            InfoRow("Código Postal", appUser.postalCode)
            InfoRow("Teléfono", appUser.phone)
        }
    }

    Spacer(modifier = Modifier.height(10.dp))

    val context = LocalContext.current

    Button(
        onClick = {
            appUser.insertNewUser(context)
            onNextPage()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
        shape = MaterialTheme.shapes.large,
    ) {
        Text(
            text = "Volver a la página de inicio",
            color = GreenishWhite,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            fontFamily = SourGummy
        )
    }
}