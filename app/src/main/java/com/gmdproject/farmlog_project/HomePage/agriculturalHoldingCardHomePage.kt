package com.gmdproject.farmlog_project.HomePage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gmdproject.farmlog_project.DBClasses.AgriculturalHolding

@Composable
fun agriculturalHoldingCardHomePage(agriculturalHolding: AgriculturalHolding){
    Card(
        modifier = Modifier.fillMaxSize().padding(10.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Información: Explotación",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            InfoRow("Nombre", agriculturalHolding.name)
            InfoRow("Ubicación", agriculturalHolding.location)
            InfoRow("Dueño", agriculturalHolding.owner)
            InfoRow("N.Registro Nacional", agriculturalHolding.registrationNumber)
        }
    }
}