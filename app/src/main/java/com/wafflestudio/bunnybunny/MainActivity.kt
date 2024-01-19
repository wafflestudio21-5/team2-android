package com.wafflestudio.bunnybunny


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wafflestudio.bunnybunny.SampleData.GoodsPostContentSample
import com.wafflestudio.bunnybunny.lib.network.api.BunnyApi
import com.wafflestudio.bunnybunny.pages.GoodsPostPage
import androidx.navigation.navArgument
import com.wafflestudio.bunnybunny.pages.SignupPage
import com.wafflestudio.bunnybunny.pages.SocialSignupPage
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
                // A surface container using the 'background' color from the theme
                //StartPage()
                //viewModel.updateGoodsPostList(Goods)
                //viewModel.updateGoodsPostContent(GoodsPostContentSample)
                //viewModel.wishToggleExample(1,true)
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
                    viewModel,
                    modifier = Modifier,
                    onNavigateToSignUp = {navController.navigate("SignupPage")},
                    onNavigateToSocialSignUp = {navController.navigate("SocialSignupPage/{idToken}")},
                    onNavigateToSignIn = {navController.navigate("TabPage")
                    }
                )
            }
            composable("SignupPage"){
                SignupPage(
                    onNavigateToStart = {navController.navigate("StartPage")},
                    context = this@MainActivity
                )
            }
            composable("SocialSignupPage/{idToken}",  arguments = listOf(navArgument("idToken") { type = NavType.StringType })) {
                SocialSignupPage(
                    onNavigateToStart = { navController.navigate("StartPage") },
                    context = this@MainActivity,
                )
            }
            composable("TabPage") {
                if(viewModel.goodsPostList.collectAsState().value.count==null && !viewModel.isgettingNewPostList){
                    Log.d("aaaa","nav call")
                    viewModel.isgettingNewPostList=true
                    viewModel.getGoodsPostList(0,viewModel.refAreaId[0])

                }
                val index= it.arguments?.getInt("index")
                if(index!=null) viewModel.selectedTabIndex.intValue=index
                TabPage(viewModel, navController = navController)
            }
            composable("GoodsPostPage/{id}") {
                val id=it.arguments!!.getString("id")
                //Log.d("aaaa","nav에서$id")
                if (id != null) {
                    GoodsPostPage(viewModel, id= id.toLong(),navController=navController)
                }
            }
        }
    }
}