package com.wafflestudio.bunnybunny

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wafflestudio.bunnybunny.pages.SignupPage
import com.wafflestudio.bunnybunny.pages.StartPage
import com.wafflestudio.bunnybunny.ui.theme.BunnybunnyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BunnybunnyTheme {
                // A surface container using the 'background' color from the theme
                MyApp()
            }
        }
    }
    @Composable
    private fun MyApp(
        modifier: Modifier = Modifier,
        navController: NavHostController = rememberNavController(),
        startDestination: String = "StartPage"
    ){
        NavHost(navController = navController, startDestination = startDestination){
            composable("StartPage"){
                StartPage(
                    onNavigateToSignUp = {navController.navigate("SignupPage")}
                )
            }
            composable("SignupPage"){
                SignupPage(
                    onNavigateToStart = {navController.navigate("StartPage")},
                    context = this@MainActivity
                )
            }
        }
    }
}