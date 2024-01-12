package com.wafflestudio.bunnybunny


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wafflestudio.bunnybunny.pages.SignupPage
import com.wafflestudio.bunnybunny.SampleData.GoodsPostSample
import com.wafflestudio.bunnybunny.lib.network.api.BunnyApi
import com.wafflestudio.bunnybunny.pages.StartPage
import com.wafflestudio.bunnybunny.pages.TabPage
import com.wafflestudio.bunnybunny.ui.theme.BunnybunnyTheme
import com.wafflestudio.bunnybunny.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var api:BunnyApi
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BunnybunnyTheme {
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
            composable("TabPage") {
                TabPage(navController = navController, viewModel = viewModel)
            }
        }
    }
}