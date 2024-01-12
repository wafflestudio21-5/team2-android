package com.wafflestudio.bunnybunny.components.compose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

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