package com.gmdproject.farmlog_project.SecondPage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.gmdproject.farmlog_project.DBClasses.getAgriculturalHolding
import com.gmdproject.farmlog_project.FrontPage.wallpaper
import com.gmdproject.farmlog_project.GlobalVariables
import com.gmdproject.farmlog_project.ui.theme.GreenishWhite
import com.gmdproject.farmlog_project.ui.theme.SourGummy

@Composable
fun AgriculturalHoldingInfoPage(
    modifier: Modifier,
    navController: NavHostController
) {
    var context = LocalContext.current
    val agriculturalHolding = getAgriculturalHolding(
        context, GlobalVariables.userId, GlobalVariables.agriculturalHoldingId
    )

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
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                if (agriculturalHolding == null) {
                    /* Haz algo para si no hay */
                } else {
                    Text(
                        text = "Información Explotación",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    UserInfoRow("Nombre", agriculturalHolding.name)
                    UserInfoRow("Ubicación", agriculturalHolding.location)
                    UserInfoRow("Propietario", agriculturalHolding.owner)
                    UserInfoRow("N.Registro Nacional", agriculturalHolding.registrationNumber)
                }
            }
        }

        Button(
            onClick = {
                navController.navigate("AgriculturalHoldingSelector")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50)
            ),
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
}

@Composable
fun UserInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = "$label:", modifier = Modifier.width(120.dp), fontWeight = FontWeight.Medium
        )
        Text(
            text = value.ifEmpty { "No proporcionado" },
            color = if (value.isEmpty()) Color.Gray else Color.Black
        )
    }
}