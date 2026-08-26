package com.gmdproject.farmlog_project.FrontPage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.gmdproject.farmlog_project.R
import com.gmdproject.farmlog_project.ui.theme.SourGummy

@Composable
fun FrontPage(modifier: Modifier, navController: NavController) {
    wallpaper()
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.farmlog_logo_324),
            contentDescription = null,
            contentScale = ContentScale.Inside,
        )
        Text(
            text = "FarmLog", color = Color.Black, fontSize = 50.sp, textAlign = TextAlign.Center, fontFamily = SourGummy
        )
        Spacer(modifier = Modifier.size(20.dp))
        Button(
            onClick = { navController.navigate("SignIn") },
            modifier = Modifier
                .width(200.dp)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
            shape = RoundedCornerShape(24.dp),
        ) {
            Text(
                text = "Inicio de sesión",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.surface,
                textAlign = TextAlign.Center,
                fontFamily = SourGummy
            )
        }
        Spacer(modifier = Modifier.size(5.dp))
        Button(
            onClick = { navController.navigate("LogIn") },
            modifier = Modifier
                .width(200.dp)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
            shape = RoundedCornerShape(24.dp),
        ) {
            Text(
                text = "Registro",
                color = MaterialTheme.colorScheme.surface,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                fontFamily = SourGummy
            )
        }
        Spacer(modifier = Modifier.size(5.dp))
//        Button(
//            onClick = { navController.navigate("About") },
//            modifier = Modifier
//                .width(200.dp)
//                .height(60.dp),
//            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
//            shape = RoundedCornerShape(24.dp),
//        ) {
//            Text(
//                text = "Más Información",
//                color = MaterialTheme.colorScheme.surface,
//                fontSize = 14.sp,
//                textAlign = TextAlign.Center,
//                fontFamily = SourGummy
//            )
//        }
    }
}

@Composable
fun wallpaper() {
    Image(
        painter = painterResource(id = R.drawable.farmlog_wallpaper),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
}
