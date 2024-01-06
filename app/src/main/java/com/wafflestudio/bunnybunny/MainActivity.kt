package com.wafflestudio.bunnybunny


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Diversity3
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Diversity3
import androidx.compose.material.icons.outlined.Forum
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import com.wafflestudio.bunnybunny.model.BottomNavItem
import com.wafflestudio.bunnybunny.pages.StartPage
import com.wafflestudio.bunnybunny.pages.TabPage
import com.wafflestudio.bunnybunny.pages.TabView
import com.wafflestudio.bunnybunny.pages.chatTab
import com.wafflestudio.bunnybunny.pages.communityTab
import com.wafflestudio.bunnybunny.pages.homeTab
import com.wafflestudio.bunnybunny.pages.myTab
import com.wafflestudio.bunnybunny.pages.tabBarItems
import com.wafflestudio.bunnybunny.ui.theme.BunnybunnyTheme
import com.wafflestudio.bunnybunny.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val viewModel: MainViewModel by viewModels()



    // creating our navController
    //
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            BunnybunnyTheme {
                // A surface container using the 'background' color from the theme
                //StartPage()
                //viewModel.goodsPostList.value=GoodsPostSample
                //TabPage(viewModel)
                Scaffold(bottomBar = { TabView(tabBarItems, navController) }) {
                    NavHost(navController = navController, startDestination = "Home") {
                        composable(homeTab.title) {
                            Text("home")
                        }
                        composable(communityTab.title) {
                            Text("community")
                        }
                        composable(chatTab.title) {
                            Text("chat")
                        }
                        composable(myTab.title) {
                            Text("my")
                        }
                    }
                }
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

        }
    }
}