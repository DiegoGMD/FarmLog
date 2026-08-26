package com.gmdproject.farmlog_project.SecondPage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gmdproject.farmlog_project.DBClasses.getAgriculturalHoldings
import com.gmdproject.farmlog_project.FrontPage.wallpaper
import com.gmdproject.farmlog_project.GlobalVariables
import com.gmdproject.farmlog_project.ui.theme.GreenishWhite
import com.gmdproject.farmlog_project.ui.theme.SourGummy

@Composable
fun AgriculturalHoldingSelector(modifier: Modifier, navController: NavHostController) {
    val context = LocalContext.current
    val userHoldings = getAgriculturalHoldings(context, GlobalVariables.userId)

    wallpaper()
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
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
            text = "Selecione Explotación:",
            fontSize = 25.sp,
            textAlign = TextAlign.Center,
            fontFamily = SourGummy
        )

        Spacer(modifier = Modifier.size(20.dp))

        userHoldings.forEach { holding ->
            ElevatedCard(
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable {
                        GlobalVariables.agriculturalHoldingId = holding.agriculturalHoldingId
                        navController.navigate("HomePage")
                    }
            ) {
                Text(
                    text = "Nombre: ${holding.name}",
                    modifier = Modifier.padding(16.dp),
                    fontFamily = SourGummy,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Left,
                )
                Text(
                    text = "Ubicación: ${holding.location}",
                    modifier = Modifier.padding(16.dp),
                    fontFamily = SourGummy,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Left,
                )
            }
        }

        Spacer(modifier = Modifier.size(5.dp))

        Button(
            onClick = { navController.navigate("AgriculturalHoldingCreator") },
            modifier = Modifier
                .width(200.dp)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
            shape = RoundedCornerShape(24.dp),
        ) {
            Text(
                text = "Añadir nueva",
                color = GreenishWhite,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                fontFamily = SourGummy
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AgriculturalHoldingSelectorPreview() {
    val navController = rememberNavController()
    AgriculturalHoldingSelector(modifier = Modifier.fillMaxSize(), navController = navController)
}