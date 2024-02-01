package com.wafflestudio.bunnybunny.pages

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Diversity3
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Diversity3
import androidx.compose.material.icons.outlined.Forum
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberImagePainter
import com.wafflestudio.bunnybunny.components.UI.bunnyColor
import com.wafflestudio.bunnybunny.components.compose.ChatContents
import com.wafflestudio.bunnybunny.components.compose.LoginInputTextField
import com.wafflestudio.bunnybunny.components.compose.NotificationsButton
import com.wafflestudio.bunnybunny.components.compose.PersonButton
import com.wafflestudio.bunnybunny.components.compose.SearchButton
import com.wafflestudio.bunnybunny.components.compose.SettingsButton
import com.wafflestudio.bunnybunny.model.BottomNavItem
import com.wafflestudio.bunnybunny.utils.formatProductTime
import com.wafflestudio.bunnybunny.viewModel.CommunityViewModel
import com.wafflestudio.bunnybunny.viewModel.MainViewModel
import com.wafflestudio.bunnybunny.data.example.EditProfileRequest
import com.wafflestudio.bunnybunny.utils.calculateMannerTempColor
import com.wafflestudio.bunnybunny.viewModel.ChatViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val homeTab = BottomNavItem(tag = "홈", title = "Home", selectedIcon = Icons.Filled.Home, unselectedIcon = Icons.Outlined.Home)
val communityTab = BottomNavItem(tag="동네생활", title="Community", selectedIcon = Icons.Filled.Diversity3, unselectedIcon = Icons.Outlined.Diversity3)
val chatTab = BottomNavItem(tag = "채팅",title="Chat", selectedIcon = Icons.Filled.Forum, unselectedIcon = Icons.Outlined.Forum)
val myTab = BottomNavItem(tag = "나의 당근",title="My", selectedIcon = Icons.Filled.Person, unselectedIcon = Icons.Outlined.Person)

// creating a list of all the tabs
val tabBarItems = listOf(homeTab, communityTab, chatTab, myTab)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabPage(index:Int?=null,chatViewModel: ChatViewModel, navController: NavController){

    val viewModel = hiltViewModel<MainViewModel>()
    val selectedTabIndex= rememberSaveable {
        mutableIntStateOf(0)
    }
    if(index!=null) selectedTabIndex.intValue=index

    val homePagelistState = rememberLazyListState()


    val token = viewModel.getOriginalToken()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(token) {
        try {
            coroutineScope.launch {
                chatViewModel.connectToUser()
                chatViewModel.getRecentMessages(255)
                delay(200)
                chatViewModel.getRecentMessages(255)
            }
        } catch (e: Exception) {
            Log.d("CHAT", e.message!!)
        }
    }

    //viewModel.currentTab.value=tabName
    Scaffold(bottomBar = { TabNavigationBar(selectedTabIndex,tabBarItems) }, topBar = { TabPageToolBar(selectedTabIndex,navController)}) {paddingValues->
        Box(
            Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues),
        ) {
            
            when(selectedTabIndex.intValue){
                0-> {
                    HomeTabPageView( listState = homePagelistState,navController = navController)
                    WritePostButton(modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(12.dp)){

                        navController.navigate("WriteGoodsPostPage")
                    }
                }
                1-> {
                    CommunityTabPageView( navController)
                    WritePostButton(modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(12.dp)){

                        navController.navigate("WriteCommunityPostPage")
                    }
                }
                2-> ChatTabPageView(chatViewModel, navController)
                3-> MyTabPageView(viewModel = viewModel, navController = navController)
            }
        }
    }


}

@Composable
fun TabNavigationBar(selectedTabIndex:MutableState<Int>,tabBarItems: List<BottomNavItem>) {
    NavigationBar(
        containerColor = if (isSystemInDarkTheme()) Color(0xFF222222) else Color.White) {
        // looping over each tab to generate the views and navigation for each item
        tabBarItems.forEachIndexed { index, tabBarItem ->
            NavigationBarItem(
                selected = selectedTabIndex.value == index,
                onClick = {
                    selectedTabIndex.value = index
                },
                icon = {
                    TabBarIconView(
                        isSelected = selectedTabIndex.value == index,
                        selectedIcon = tabBarItem.selectedIcon,
                        unselectedIcon = tabBarItem.unselectedIcon,
                        title = tabBarItem.title,
                        badgeAmount = tabBarItem.badgeAmount
                    )
                },
                label = {Text(tabBarItem.tag)})
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabBarIconView(
    isSelected: Boolean,
    selectedIcon: ImageVector,
    unselectedIcon: ImageVector,
    title: String,
    badgeAmount: Int? = null
) {
    BadgedBox(badge = { TabBarBadgeView(badgeAmount) }) {
        Icon(
            imageVector = if (isSelected) {selectedIcon} else {unselectedIcon},
            contentDescription = title
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TabBarBadgeView(count: Int? = null) {
    if (count != null) {
        Badge {
            Text(count.toString())
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabPageToolBar(selectedTabIndex:MutableState<Int>,navController: NavController) {
    Column {
        val invColor = when(isSystemInDarkTheme()){
            true -> Color.White
            else -> Color.Black
        }
        TopAppBar(
            title = {
                    Text(text=when(selectedTabIndex.value){
                        0->""
                        1->""
                        2->"채팅"
                        3->"나의당근"
                        else->""
                    })
            },
            navigationIcon = {
                Row{

                }
            },
            actions = {
                when(selectedTabIndex.value){
                    0-> {
                        SearchButton()
                        NotificationsButton()
                    }
                    1-> {
                        PersonButton()
                        SearchButton()
                        NotificationsButton()
                    }
                    2-> {
                        NotificationsButton()
                    }
                    3-> {
                        SettingsButton()
                    }
                }

            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                navigationIconContentColor = invColor,
                titleContentColor = invColor, // Color for the title
                actionIconContentColor = invColor // Color for action icons
            ),
        )
        Divider(
            Modifier
                .fillMaxWidth()
                .height(1.dp))
    }


}

@Composable
fun WritePostButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    ExtendedFloatingActionButton(
        modifier = modifier,
        onClick = { onClick() },
        icon = { Icon(Icons.Filled.Add, "Edit") },
        text = { Text(text = "글쓰기") },
        contentColor = Color.White,
        containerColor = bunnyColor,
        shape = CircleShape
    )
}
@Composable
fun HomeTabPageView(listState:LazyListState,navController: NavController){
    val viewModel = hiltViewModel<MainViewModel>()
    LaunchedEffect(key1 = true){
        if(viewModel.CanCallFirstGoodsPostList()){
            viewModel.disableCallFirstGoodsPostList()
            Log.d("aaaa", "updateGoodsPostList called in HomeTabPageView LaunchedEffect")
            viewModel.updateGoodsPostList(
                distance = 0,
                areaId = viewModel.getRefAreaId()[0],
                count=0,cur=null,seed=null)
        }
    }
    val itemList = viewModel.goodsPostList.collectAsLazyPagingItems()
    Log.d("aaaa123", "vcalled")



    LazyColumn(state = listState){
        item {
            //물품 필터
        }

        items(itemList){
            //Log.d("aaaa123", it.toString())
            it ?: return@items

            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 18.dp)
                .clickable {
                    Log.d("aaaa", it.id.toString())
                    //Log.d("aaaa","GoodsPostPage/${it.id}")
                    navController.navigate("GoodsPostPage/${it.id}")
                }
            ){
                Row(Modifier.align(Alignment.CenterStart)) {
                    val painter = rememberImagePainter(data = it.repImg)

                    Image(
                        painter = painter,
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .border(
                                1.dp,
                                Color.Gray.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(corner = CornerSize(8.dp))
                            )
                            .clip(RoundedCornerShape(corner = CornerSize(8.dp)))
                            .clipToBounds(),
                        contentScale = ContentScale.Crop,
                    )
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text(text = it.title, fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = it.tradingLocation+"·"+ formatProductTime(it.createdAt, it.refreshedAt), color = Color.Gray, fontSize = 13.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = it.sellPrice.toString()+"원", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        if (it.wishCnt > 0 || it.chatCnt > 0) {
                            Text(text = (if (it.wishCnt > 0) "관심 " + it.wishCnt.toString() else "") + (if (it.chatCnt > 0) "채팅 " + it.chatCnt.toString() else ""))
                        }
                    }
                }
            }
            Divider(
                color = Color.Gray.copy(alpha = 0.2f),
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }

    }

}
@Composable
fun CommunityTabPageView(navController: NavController){
    val viewModel = hiltViewModel<CommunityViewModel>()
    val itemList = viewModel.communityPostList.collectAsLazyPagingItems()
    val listState = rememberLazyListState()

    LaunchedEffect(key1 = true){
        Log.d("aaaa123", "hihihi")

        if(viewModel.CanCallFirstCommunityPostList()){
            viewModel.disableCallFirstCommunityPostList()
            Log.d("aaaa", "updateCommunityPostList called in CommunityTabPageView LaunchedEffect")
            viewModel.updateCommunityPostList(
                distance = 0,
                areaId = viewModel.getRefAreaId()[0],
                count=0,cur=null,seed=null)
        }
    }
    LazyColumn(state = listState){
        item {
            //물품 필터
        }
        items(itemList){
            it ?: return@items
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(start = 16.dp, end = 16.dp)
                .clickable {
                    //Log.d("aaaa", it.id.toString())
                    //Log.d("aaaa","GoodsPostPage/${it.id}")
                    navController.navigate("CommunityPostPage/${it.id}")
                }
            ){
                Column{
                    Row {
                        Column{
                            Text(it.title)
                            Text(it.description, overflow = TextOverflow.Clip, maxLines = 1)

                        }
                        val painter = rememberImagePainter(data = it.repImg)
                        Image(
                            painter = painter,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(100.dp)
                        )

                    }

                    Row {
                        Text("${it.areaName}·${it.createdAt}·조회${it.viewCnt}")
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(imageVector = Icons.Outlined.ThumbUp, contentDescription ="like" )
                        Text(it.likeCnt.toString())
                        Icon(imageVector = Icons.Outlined.ChatBubbleOutline, contentDescription ="chat" )
                        Text(it.chatCnt.toString())
                    }
                }
            }
            Divider(
                Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp))
        }

    }

}
@Composable
fun ChatTabPageView(chatViewModel: ChatViewModel, navController: NavController){
    chatViewModel.getChannelList()


    ChatContents(modifier = Modifier, viewModel = chatViewModel, navController = navController)
//    ChatRoomScreen(viewModel = chatViewModel)
}
