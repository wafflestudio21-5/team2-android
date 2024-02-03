package com.wafflestudio.bunnybunny.pages

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Diversity3
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Diversity3
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Forum
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
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
import androidx.compose.runtime.remember
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.wafflestudio.bunnybunny.components.UI.bunnyColor
import com.wafflestudio.bunnybunny.components.compose.ChatContents
import com.wafflestudio.bunnybunny.components.compose.CurrentAreaWithDropDownMenu
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
import com.wafflestudio.bunnybunny.lib.network.dto.GoodsPostPreview
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
fun TabPage( viewModel: MainViewModel, itemList: LazyPagingItems<GoodsPostPreview>, homePagelistState: LazyListState ,index:Int?=null,mainViewModel: MainViewModel, chatViewModel: ChatViewModel, navController: NavController){

//    val viewModel = hiltViewModel<MainViewModel>()
    val selectedTabIndex= rememberSaveable {
        mutableIntStateOf(0)
    }
    if(index!=null) selectedTabIndex.intValue=index




//    Log.d("cccc", itemList.itemCount.toString())


    val token = mainViewModel.getOriginalToken()
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
    Scaffold(bottomBar = { TabNavigationBar(selectedTabIndex,tabBarItems) }, topBar = { TabPageToolBar(selectedTabIndex,navController, viewModel)}) {paddingValues->
        Box(
            Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues)
        ) {
            
            when(selectedTabIndex.intValue){
                0-> {
                    HomeTabPageView(viewModel = viewModel, listState = homePagelistState,itemList,navController = navController)
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
                3-> MyTabPageView(viewModel = mainViewModel, navController = navController)
            }
        }
    }


}

@Composable
fun TabNavigationBar(selectedTabIndex:MutableState<Int>,tabBarItems: List<BottomNavItem>) {
    NavigationBar(
        containerColor = Color.White) {
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
fun TabPageToolBar(selectedTabIndex:MutableState<Int>,navController: NavController, viewModel: MainViewModel) {
    Column {
        TopAppBar(
            title = { when(selectedTabIndex.value) {
                        0 -> CurrentAreaWithDropDownMenu(viewModel = viewModel, navController = navController)
                        1 -> CurrentAreaWithDropDownMenu(viewModel = viewModel, navController = navController)
                        2 -> Text(text="채팅")
                        3 -> Text(text="나의 당근")
                        else -> Text(text="")
                        }
                    },
            navigationIcon = {
                Row{

                }
            },
            actions = {
                when(selectedTabIndex.value){
                    0-> {
                        SearchButton(navController)
                        NotificationsButton()
                    }
                    1-> {
                        PersonButton()
                        SearchButton(navController)
                        NotificationsButton()
                    }
                    2-> {
                        NotificationsButton()
                    }
                    3-> {
                        SettingsButton(navController)
                    }
                }

            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                navigationIconContentColor = Color.Black,
                titleContentColor = Color.Black, // Color for the title
                actionIconContentColor = Color.Black // Color for action icons
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
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeTabPageView(viewModel: MainViewModel, listState:LazyListState, itemList: LazyPagingItems<GoodsPostPreview>, navController: NavController){
//    val viewModel = hiltViewModel<MainViewModel>()
    var isRefreshing by remember { mutableStateOf(false) }

    val distance = viewModel.currentDistance.collectAsState()
    val currentRefAreaId = viewModel.currentRefAreaId.collectAsState()

    val pullRefreshState = rememberPullRefreshState(refreshing = isRefreshing, onRefresh = {
        isRefreshing = true
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.updateGoodsPostList(
                distance = distance.value,
                areaId = currentRefAreaId.value[0],
                count = 0, cur = null, seed = null
            )
            isRefreshing = false
        }
    })
    LaunchedEffect(key1 = true){
        if(viewModel.CanCallFirstGoodsPostList()){
            viewModel.disableCallFirstGoodsPostList()
            Log.d("aaaa", "updateGoodsPostList called in HomeTabPageView LaunchedEffect")
            viewModel.updateGoodsPostList(
                distance = distance.value,
                areaId = currentRefAreaId.value[0],
                count=0,cur=null,seed=null)
        }
    }

    Box(modifier = Modifier
        .fillMaxWidth()
        .pullRefresh(pullRefreshState), contentAlignment = Alignment.TopCenter) {
        Box(modifier = Modifier.zIndex(5f)) {
            PullRefreshIndicator(refreshing = isRefreshing, state = pullRefreshState)
        }
        LazyColumn(state = listState) {
            item {
                //물품 필터
            }

            items(itemList) {
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
                ) {
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
                            Text(
                                text = it.tradingLocation + "·" + formatProductTime(
                                    it.createdAt,
                                    it.refreshedAt
                                ), color = Color.Gray, fontSize = 13.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = it.sellPrice.toString() + "원", fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(3.dp),
                            ) {
                                Spacer(Modifier.weight(1f))
                                if (it.wishCnt > 0) {
                                    Image(
                                        imageVector = Icons.Outlined.FavoriteBorder,
                                        contentDescription = null
                                    )
                                    Text(text = it.wishCnt.toString())
                                }
                                if (it.chatCnt > 0) {
                                    Text("채팅")
                                    Text(text = it.chatCnt.toString())
                                }
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

}
@Composable
fun CommunityTabPageView(navController: NavController){
    val viewModel = hiltViewModel<CommunityViewModel>()
    val itemList = viewModel.communityPostList.collectAsLazyPagingItems()
    val listState = rememberLazyListState()

    val mainViewModel = hiltViewModel<MainViewModel>()
    val distance = mainViewModel.currentDistance.collectAsState()
    val currentRefAreaId = mainViewModel.currentRefAreaId.collectAsState()

    LaunchedEffect(key1 = true){
        Log.d("aaaa123", "hihihi")

        if(viewModel.CanCallFirstCommunityPostList()){
            viewModel.disableCallFirstCommunityPostList()
            Log.d("aaaa", "updateCommunityPostList called in CommunityTabPageView LaunchedEffect")
            viewModel.updateCommunityPostList(
                distance = distance.value,
                areaId = currentRefAreaId.value[0],
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
                Column(
                    verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Top),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .background(color = Color(0xFFFFFFFF))
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.height(80.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(if (it.repImg.isNotEmpty()) 0.8f else 1f)
                            ) {
                                Text(
                                    text = it.title,
                                    style = TextStyle(
                                        fontSize = 18.sp,
                                        lineHeight = 20.sp,
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFF000000),
                                    )
                                )
                                Text(
                                    text = it.description,
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFF000000),
                                    ),
                                    overflow = TextOverflow.Clip,
                                    maxLines = 1
                                )
                            }
                            if (it.repImg.isNotEmpty()) {
                                // 이미지가 있는 경우
                                val painter = rememberImagePainter(data = it.repImg)
                                Image(
                                    painter = painter,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .width(80.dp)
                                        .clip(RoundedCornerShape(5.dp)),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("${it.areaName}·${formatProductTime(it.createdAt,it.createdAt)}·조회${it.viewCnt}", fontSize = 14.sp)
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(imageVector = Icons.Outlined.ThumbUp, contentDescription = "like")
                                Text(it.likeCnt.toString())
                                Icon(imageVector = Icons.Outlined.ChatBubbleOutline, contentDescription = "chat")
                                Text(it.chatCnt.toString())
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
//                    Row {
//                        Column{
//                            Text(it.title)
//                            Text(it.description, overflow = TextOverflow.Clip, maxLines = 1)
//
//                        }
//                        val painter = rememberImagePainter(data = it.repImg)
//                        if (it.repImg != "") {
//                            Image(
//                                painter = painter,
//                                contentDescription = null,
//                                modifier = Modifier
//                                    .fillMaxHeight()
//                                    .width(100.dp)
//                            )
//                        }
//
//                    }


                }
            }
        }

    }

}
@Composable
fun ChatTabPageView(chatViewModel: ChatViewModel, navController: NavController){
    chatViewModel.getChannelList()


    ChatContents(modifier = Modifier, viewModel = chatViewModel, navController = navController)
//    ChatRoomScreen(viewModel = chatViewModel)
}
