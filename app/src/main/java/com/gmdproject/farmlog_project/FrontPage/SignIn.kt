package com.gmdproject.farmlog_project.FrontPage

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.gmdproject.farmlog_project.DBClasses.AppUser
import com.gmdproject.farmlog_project.ui.theme.SourGummy

@Composable
fun SignIn(modifier: Modifier, navController: NavController) {
    val configuration = LocalConfiguration.current
    configuration.orientation = Configuration.ORIENTATION_PORTRAIT

    var currentPage by remember { mutableStateOf(0) }
    var appUser by remember { mutableStateOf(AppUser()) }

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
            text = "Registro",
            color = Color.Black,
            fontSize = 25.sp,
            textAlign = TextAlign.Center,
            fontFamily = SourGummy
        )
        Spacer(modifier = Modifier.height(10.dp))



        when (currentPage) {
            0 -> PersonalInfoPage(appUser = appUser,
                onDataUpdated = { updatedData -> appUser = updatedData },
                onNextPage = { currentPage = 1 })

            1 -> AddressInfoPage(appUser = appUser,
                onDataUpdated = { updatedData -> appUser = updatedData },
                onPreviousPage = { currentPage = 0 },
                onNextPage = { currentPage = 2 })

            2 -> UserInfoPage(
                appUser = appUser,
                onNextPage = { navController.navigate("FrontPage") })
        }
    }
}