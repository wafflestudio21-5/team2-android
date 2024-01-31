package com.wafflestudio.bunnybunny.pages

import android.annotation.SuppressLint
import android.util.JsonToken
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Diversity3
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Diversity3
import androidx.compose.material.icons.outlined.Forum
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.wafflestudio.bunnybunny.components.compose.BackButton
import com.wafflestudio.bunnybunny.components.compose.HomeButton
import com.wafflestudio.bunnybunny.components.compose.LoginInputTextField
import com.wafflestudio.bunnybunny.components.compose.MoreVertButton
import com.wafflestudio.bunnybunny.components.compose.NotificationsButton
import com.wafflestudio.bunnybunny.components.compose.PersonButton
import com.wafflestudio.bunnybunny.components.compose.SearchButton
import com.wafflestudio.bunnybunny.components.compose.SettingsButton
import com.wafflestudio.bunnybunny.components.compose.ShareButton
import com.wafflestudio.bunnybunny.data.example.EditProfileRequest
import com.wafflestudio.bunnybunny.model.BottomNavItem
import com.wafflestudio.bunnybunny.viewModel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

val homeTab = BottomNavItem(tag = "홈", title = "Home", selectedIcon = Icons.Filled.Home, unselectedIcon = Icons.Outlined.Home)
val communityTab = BottomNavItem(tag="동네생활", title="Community", selectedIcon = Icons.Filled.Diversity3, unselectedIcon = Icons.Outlined.Diversity3)
val chatTab = BottomNavItem(tag = "채팅",title="Chat", selectedIcon = Icons.Filled.Forum, unselectedIcon = Icons.Outlined.Forum)
val myTab = BottomNavItem(tag = "나의 당근",title="My", selectedIcon = Icons.Filled.Person, unselectedIcon = Icons.Outlined.Person)

// creating a list of all the tabs
val tabBarItems = listOf(homeTab, communityTab, chatTab, myTab)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabPage(viewModel:MainViewModel,navController: NavController){


    //viewModel.currentTab.value=tabName
    Scaffold(bottomBar = { TabNavigationBar(viewModel,tabBarItems) }, topBar = { TabPageToolBar(viewModel,navController)}) {paddingValues->
        Box(
            Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues),
        ) {
            
            when(viewModel.selectedTabIndex.value){
                0-> {
                    HomeTabPageView(viewModel = viewModel, navController = navController)
                    WritePostButton(modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)){
                        navController.navigate("WriteGoodsPostPage")
                    }
                }
                1-> {
                    CommunityTabPageView()
                    WritePostButton(modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)){
                        //navController.navigate(대출 동네생활 글쓰기 페이지)
                    }
                }
                2-> ChatTabPageView()
                3-> MyTabPageView(viewModel = viewModel, navController = navController)

            }
        }
    }


}

@Composable
fun TabNavigationBar(viewModel: MainViewModel,tabBarItems: List<BottomNavItem>) {
    NavigationBar {
        // looping over each tab to generate the views and navigation for each item
        tabBarItems.forEachIndexed { index, tabBarItem ->
            NavigationBarItem(
                selected = viewModel.selectedTabIndex.value == index,
                onClick = {
                    viewModel.selectedTabIndex.value = index
                },
                icon = {
                    TabBarIconView(
                        isSelected = viewModel.selectedTabIndex.value == index,
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
fun TabPageToolBar(viewModel: MainViewModel,navController: NavController) {
    Column {
        TopAppBar(
            title = {
                    Text(text=when(viewModel.selectedTabIndex.value){
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
                when(viewModel.selectedTabIndex.value){
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
fun WritePostButton(modifier: Modifier= Modifier, onClick: () -> Unit) {
    ExtendedFloatingActionButton(
        modifier = modifier,
        onClick = { onClick() },
        icon = { Icon(Icons.Filled.Edit, "Edit") },
        text = { Text(text = "글쓰기") },
    )
}
@Composable
fun HomeTabPageView(viewModel:MainViewModel,navController: NavController){
    val listState = rememberLazyListState()
    val isNeedNewRequest= remember { derivedStateOf {
        listState.firstVisibleItemIndex>viewModel.goodsPostList.value.data.size-10&&!viewModel.goodsPostList.value.isLast&&viewModel.goodsPostList.value.count!=0
    }
    }
    LazyColumn(state = listState){
        item {
            //물품 필터
        }
        items(viewModel.goodsPostList.value.data){
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(start = 16.dp, end = 16.dp)
                .clickable {
                    Log.d("aaaa", it.id.toString())
                    //Log.d("aaaa","GoodsPostPage/${it.id}")
                    navController.navigate("GoodsPostPage/${it.id}")
                }
            ){
                Row {
                    val painter = rememberImagePainter(data = it.repImg)

                    Image(
                        painter = painter,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(100.dp)
                    )
                    Column {
                        Text(text = it.title)
                        Text(text = it.tradingLocation+"·"+it.refreshedAt)
                        Text(text = it.sellPrice.toString()+"원")
                        Text(text = (if(it.wishCnt>0)"관심 "+it.wishCnt.toString() else "")+(if(it.chatCnt>0)"채팅 "+it.chatCnt.toString() else ""))
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
    if(isNeedNewRequest.value&&!viewModel.isgettingNewPostList){
        viewModel.isgettingNewPostList=true
        Log.d("aaaa","scroll call")
        viewModel.getGoodsPostList(0,viewModel.getRefAreaId()[0])
    }
}
@Composable
fun CommunityTabPageView(){

}
@Composable
fun ChatTabPageView(){

}
