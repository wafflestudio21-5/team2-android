package com.wafflestudio.bunnybunny.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Diversity3
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Diversity3
import androidx.compose.material.icons.outlined.Forum
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.wafflestudio.bunnybunny.model.BottomNavItem
import com.wafflestudio.bunnybunny.viewModel.MainViewModel

val homeTab = BottomNavItem(tag = "홈", title = "Home", selectedIcon = Icons.Filled.Home, unselectedIcon = Icons.Outlined.Home)
val communityTab = BottomNavItem(tag="동네생활", title="Community", selectedIcon = Icons.Filled.Diversity3, unselectedIcon = Icons.Outlined.Diversity3)
val chatTab = BottomNavItem(tag = "채팅",title="Chat", selectedIcon = Icons.Filled.Forum, unselectedIcon = Icons.Outlined.Forum)
val myTab = BottomNavItem(tag = "나의 당근",title="My", selectedIcon = Icons.Filled.Person, unselectedIcon = Icons.Outlined.Person)

// creating a list of all the tabs
val tabBarItems = listOf(homeTab, communityTab, chatTab, myTab)
@Composable
fun TabPage(tabName:String,tabBarItems: List<BottomNavItem>, navController: NavController, viewModel: MainViewModel){
    viewModel.currentTab.value=tabName


    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        /*
        TitleBar()
        Box(Modifier.fillMaxSize()){
            when(viewModel.currentTab.value){
                "Home"->HomeTabLazyColumn(viewModel)
                "Community"->CommunityTabLazyColumn()
                "Chat"->ChatTabLazyColumn()
                "My"->MyTabLazyColumn()
            }
            if(viewModel.currentTab.value=="Home"||viewModel.currentTab.value=="Community"){
                WritePostButton()
            }
        }*/
    }
}

@Composable
fun TabView(tabBarItems: List<BottomNavItem>, navController: NavController) {

    var selectedTabIndex by rememberSaveable {
        mutableStateOf(0)
    }

    NavigationBar {
        // looping over each tab to generate the views and navigation for each item
        tabBarItems.forEachIndexed { index, tabBarItem ->
            NavigationBarItem(
                selected = selectedTabIndex == index,
                onClick = {
                    selectedTabIndex = index
                    navController.navigate(tabBarItem.title)
                },
                icon = {
                    TabBarIconView(
                        isSelected = selectedTabIndex == index,
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
@Composable
fun TitleBar(){

}


@Composable
fun WritePostButton(){

}

@Composable
fun HomeTabLazyColumn(viewModel:MainViewModel){
    /*
    LazyColumn{
        item {

        }
        items(viewModel.goodsPostList.value){
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
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
                        Text(text = it.region+"·"+it.passedTime)
                        Text(text = it.prise.toString()+"원")
                        Text(text = (if(it.like>0)"관심 "+it.like.toString() else "")+(if(it.chat>0)"채팅 "+it.chat.toString() else ""))
                    }
                }
            }
            Divider(
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
            )
        }

    }*/
}
@Composable
fun CommunityTabLazyColumn(){

}
@Composable
fun ChatTabLazyColumn(){

}
@Composable
fun MyTabLazyColumn(){

}