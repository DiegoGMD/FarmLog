package com.gmdproject.farmlog_project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gmdproject.farmlog_project.FrontPage.FrontPage
import com.gmdproject.farmlog_project.FrontPage.SignIn
import com.gmdproject.farmlog_project.FrontPage.LogIn
import com.gmdproject.farmlog_project.HomePage.HomePage
import com.gmdproject.farmlog_project.SecondPage.AgriculturalHoldingCreator
import com.gmdproject.farmlog_project.SecondPage.AgriculturalHoldingInfoPage
import com.gmdproject.farmlog_project.SecondPage.AgriculturalHoldingSelector
import com.gmdproject.farmlog_project.ui.theme.FarmLogProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FarmLogProjectTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "FrontPage") {
                        composable("FrontPage") {
                            FrontPage(
                                Modifier.padding(innerPadding),
                                navController
                            )
                        }
                        composable("LogIn") {
                            SignIn(
                                Modifier.padding(innerPadding),
                                navController
                            )
                        }
                        composable("SignIn") {
                            LogIn(
                                Modifier.padding(innerPadding),
                                navController
                            )
                        }
                        composable("AgriculturalHoldingSelector") {
                            AgriculturalHoldingSelector(
                                Modifier.padding(innerPadding),
                                navController
                            )
                        }
                        composable("AgriculturalHoldingCreator") {
                            AgriculturalHoldingCreator(
                                Modifier.padding(innerPadding),
                                navController
                            )
                        }
//                        composable("AgriculturalHoldingInfoPage") {
//                            AgriculturalHoldingInfoPage(
//                                Modifier.padding(innerPadding),
//                                navController
//                            )
//                        }
                        composable("HomePage") {
                            HomePage(
                                Modifier.padding(innerPadding)
                            )
                        }
                    }
                }
            }
        }
    }
}