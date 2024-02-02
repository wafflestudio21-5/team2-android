package com.wafflestudio.bunnybunny.components.compose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.NotificationsOff
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.wafflestudio.bunnybunny.viewModel.MainViewModel

@Composable
fun BackButton(navController: NavController){
    IconButton(onClick = { navController.popBackStack()}) {
        Icon(
            imageVector = Icons.Outlined.ArrowBackIos,
            contentDescription = "Back"
        )
    }
}
@Composable
fun HomeButton(navController: NavController){
    IconButton(onClick = { navController.navigate("TabPage?index=0"){
        popUpTo(navController.graph.startDestinationRoute!!) { inclusive = true }
    } }) {
        Icon(
            imageVector = Icons.Outlined.Home,
            contentDescription = "Home"
        )
    }
}
@Composable
fun ShareButton(){
    IconButton(onClick = {}) {
        Icon(
            imageVector = Icons.Outlined.Share,
            contentDescription = "Share"
        )
    }
}
@Composable
fun MoreVertButton(){
    IconButton(onClick = {}) {
        Icon(
            imageVector = Icons.Outlined.MoreVert,
            contentDescription = "MoreVert"
        )
    }
}
@Composable
fun SearchButton(){
    IconButton(onClick = {}) {
        Icon(
            imageVector = Icons.Outlined.Search,
            contentDescription = "Search"
        )
    }
}
@Composable
fun NotificationsButton(){
    IconButton(onClick = {}) {
        Icon(
            imageVector = Icons.Outlined.Notifications,
            contentDescription = "Notifications"
        )
    }
}
@Composable
fun PersonButton(){
    IconButton(onClick = {}) {
        Icon(
            imageVector = Icons.Outlined.Person,
            contentDescription = "Person"
        )
    }
}
@Composable
fun SettingsButton(navController: NavController){
    val viewModel = hiltViewModel<MainViewModel>()
    IconButton(onClick =  {
        viewModel.logOutApp()
        navController.navigate("StartPage"){
            popUpTo("StartPage") { inclusive = false }
        }
    }) {
        Icon(
            imageVector = Icons.Outlined.Settings,
            contentDescription = "Settings"
        )
    }
}
@Composable
fun NotificationsButton(enabled:Boolean){
    IconButton(onClick = {}) {
        Icon(
            imageVector = if(enabled)Icons.Filled.Notifications else Icons.Outlined.NotificationsOff,
            contentDescription = "Settings"
        )
    }
}
@Composable
fun CloseButton(navController: NavController){
    IconButton(onClick = {navController.popBackStack()}) {
        Icon(
            imageVector =Icons.Outlined.Close ,
            contentDescription = "Settings"
        )
    }
}
