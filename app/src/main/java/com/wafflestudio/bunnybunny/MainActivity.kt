package com.wafflestudio.bunnybunny


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wafflestudio.bunnybunny.SampleData.GoodsPostContentSample
import com.wafflestudio.bunnybunny.lib.network.api.BunnyApi
import com.wafflestudio.bunnybunny.pages.GoodsPostPage
import androidx.navigation.navArgument
import com.wafflestudio.bunnybunny.pages.GalleryViewPage
import com.wafflestudio.bunnybunny.pages.SignupPage
import com.wafflestudio.bunnybunny.model.ParcelableMutableList
import com.wafflestudio.bunnybunny.pages.AreaChoosePage
import com.wafflestudio.bunnybunny.pages.ChatRoomPage
import com.wafflestudio.bunnybunny.pages.CommunityPostPage
import com.wafflestudio.bunnybunny.pages.ProfileEditPage
import com.wafflestudio.bunnybunny.pages.ProfilePage
import com.wafflestudio.bunnybunny.pages.SocialAreaChoosePage
import com.wafflestudio.bunnybunny.pages.SocialSignupPage
import com.wafflestudio.bunnybunny.pages.StartPage
import com.wafflestudio.bunnybunny.pages.TabPage
import com.wafflestudio.bunnybunny.pages.WishListPage
import com.wafflestudio.bunnybunny.pages.WriteCommunityPostPage
import com.wafflestudio.bunnybunny.pages.WriteGoodsPostPage
import com.wafflestudio.bunnybunny.pages.fetchGalleryImages
import com.wafflestudio.bunnybunny.ui.theme.BunnybunnyTheme
import com.wafflestudio.bunnybunny.viewModel.ChatViewModel
import com.wafflestudio.bunnybunny.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var api:BunnyApi
    private val viewModel: MainViewModel by viewModels()
    private val chatViewModel: ChatViewModel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BunnybunnyTheme {
                CompositionLocalProvider(LocalTextSelectionColors provides TextSelectionColors(handleColor = Color.Black, backgroundColor = Color.Black.copy(alpha = 0.3f))) {
                    // A surface container using the 'background' color from the theme
                    //StartPage()
                    //viewModel.updateGoodsPostList(Goods)
                    //viewModel.updateGoodsPostContent(GoodsPostContentSample)
                    //viewModel.wishToggleExample(1,true)
                    //viewModel.setSelectedTabIndex(0)
                    //Log.d("aaaa","Token:${viewModel.getToken()}")
                    viewModel.initializeApp()
                    if (viewModel.getOriginalToken() != null) {
                        MyApp(startDestination = "TabPage")
                    } else {
                        MyApp()
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
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {

            NavHost(navController = navController, startDestination = startDestination) {
                composable("StartPage") {
                    StartPage(
                        viewModel,
                        modifier = Modifier,
                        onNavigateToSignUp = { navController.navigate("SignupPage")},
                        onNavigateToSocialSignUp = { idToken -> navController.navigate("SocialSignupPage/$idToken") },
                        onNavigateToSignIn = {
                            navController.navigate("TabPage")
                        }
                    )
                }
                composable("SignupPage") { navBackStackEntry ->
                    SignupPage(
                        onNavigateToAreaSearch  = { emailInput, pwInput, nickname -> navController.navigate("AreaChoosePage/${emailInput}/${pwInput}/${nickname}") {
                        } },
                        context = this@MainActivity
                    )
                }
                composable(
                    "SocialSignupPage/{idToken}",
                    arguments = listOf(
                        navArgument("idToken") { type = NavType.StringType })
                ) {navBackStackEntry ->
                    val idToken = navBackStackEntry.arguments?.getString("idToken") ?: ""
                    SocialSignupPage(
                        onNavigateToAreaSearch  = { nickname, idToken -> navController.navigate("SocialAreaChoosePage/${nickname}/${idToken}") },
                        idToken = idToken,
                        context = this@MainActivity,
                    )
                }
                composable("AreaChoosePage/{emailInput}/{pwInput}/{nickname}",
                    arguments = listOf(
                        navArgument("emailInput") { type = NavType.StringType; },
                        navArgument("pwInput") { type = NavType.StringType },
                        navArgument("nickname") { type = NavType.StringType }) )
                    { navBackStackEntry ->
                    // NavBackStackEntry에서 변수들을 추출
                        val emailInput = navBackStackEntry.arguments?.getString("emailInput") ?: ""
                        val pwInput = navBackStackEntry.arguments?.getString("pwInput") ?: ""
                        val nickname = navBackStackEntry.arguments?.getString("nickname") ?: ""
                        Log.d("MA_CHECK", "$emailInput")
                        AreaChoosePage (
                            emailInput, pwInput, nickname, onNavigateToStart = {navController.navigate("StartPage")}
                        )
                }

                composable("SocialAreaChoosePage/{nickname}/{idToken}",
                    arguments = listOf(
                        navArgument("nickname") { type = NavType.StringType },
                        navArgument("idToken") { type = NavType.StringType }))
                { navBackStackEntry ->
                    // NavBackStackEntry에서 변수들을 추출

                    val nickname = navBackStackEntry.arguments?.getString("nickname") ?: ""
                    val idToken = navBackStackEntry.arguments?.getString("idToken") ?: ""

                    SocialAreaChoosePage (
                        nickname, idToken, onNavigateToSignIn = {navController.navigate("StartPage")}
                    )
                }

                composable("TabPage") {
                    val index = it.arguments?.getInt("index")
                    /*
                    when(index){
                        0->{

                            if(viewModel.goodsPostList.collectAsState().value.count==null && !viewModel.isgettingNewPostList){
                                Log.d("aaaa","nav call")
                                viewModel.isgettingNewPostList=true
                                viewModel.getGoodsPostList(0,viewModel.getRefAreaId()[0])
                            }
                        }
                        1->{
                            if(viewModel.communityPostList.collectAsState().value.count==null && !viewModel.isgettingNewPostList){
                                Log.d("aaaa","nav call")
                                viewModel.isgettingNewPostList=true
                                viewModel.getCommunityPostList(0,viewModel.getRefAreaId()[0])
                            }
                        }
                    }*/
                    TabPage(index = index, chatViewModel = chatViewModel, navController = navController)
                }
                /*
                composable("TabPage",
                    ){
                    if (viewModel.goodsPostList.collectAsState().value.count==null && !viewModel.isgettingNewPostList){
                        Log.d("aaaa","nav call")
                        viewModel.isgettingNewPostList=true
                        viewModel.getGoodsPostList(0,viewModel.getRefAreaId()[0])
                    }
                    val index= it.arguments?.getInt("index")
                    if(index!=null) viewModel.selectedTabIndex.intValue=index
                    TabPage(viewModel, chatViewModel, navController = navController)
                }*/
                composable("GoodsPostPage/{id}") {
                    val id=it.arguments!!.getString("id")
                    //Log.d("aaaa","nav에서$id")
                    if (id != null) {
                        GoodsPostPage(viewModel, id= id.toLong(),navController=navController)
                    }
                }
                composable("CommunityPostPage/{id}") {
                    val id=it.arguments!!.getString("id")
                    //Log.d("aaaa","nav에서$id")
                    if (id != null) {
                        CommunityPostPage(id= id.toLong(),navController=navController)
                    }
                }
                composable("ChatRoomPage/{channelId}",
                    arguments = listOf(
                        navArgument("channelId") { type = NavType.LongType }))
                { navBackStackEntry ->
                    // NavBackStackEntry에서 변수들을 추출
                    val channelId = navBackStackEntry.arguments?.getLong("channelId") ?: 0

                    ChatRoomPage (
                        modifier = Modifier, viewModel = chatViewModel, channelId
                    )
                }
                composable("WriteGoodsPostPage") {
                    WriteGoodsPostPage(viewModel, navController)
                }
                composable("WriteCommunityPostPage") {
                    WriteCommunityPostPage( navController)
                }
                composable("GalleryViewPage"){
                    viewModel.updateGalleryImages(fetchGalleryImages(this@MainActivity))
                    viewModel.updateSelectedImages(listOf())
                    GalleryViewPage(viewModel,navController)
                }
                composable("WishListPage") {
                    WishListPage(
                        viewModel = viewModel,
                        navController = navController
                    )
                }
                composable("ProfilePage"){
                    ProfilePage(viewModel,navController)
                }
                composable("ProfileEditPage"){
                    ProfileEditPage(viewModel, navController)
                }
            }
        }
    }
}