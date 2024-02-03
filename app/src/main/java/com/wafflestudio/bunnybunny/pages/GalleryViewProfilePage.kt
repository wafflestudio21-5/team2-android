package com.wafflestudio.bunnybunny.pages

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.wafflestudio.bunnybunny.components.compose.BackButton
import com.wafflestudio.bunnybunny.model.ToggleImageItem
import com.wafflestudio.bunnybunny.viewModel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.File

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryViewProfilePage(viewModel: MainViewModel, navController: NavController) {
    val galleryImages by viewModel.galleryImages.collectAsState()
    val selectedImage by viewModel.profileImage.collectAsState()
    Scaffold(topBar = {GalleryViewProfilePageToolBar(selectedImage,viewModel,navController)}){paddingValues->
        Column(modifier = Modifier.padding(paddingValues)){
            GalleryViewProfile(galleryImages, selectedImage, viewModel)
        }
    }

}

@Composable
fun GalleryViewProfile(galleryImages:List<ToggleImageItem>, selectedImage:String, viewModel: MainViewModel) {
    Log.d("aaaa","GalleryProfileView called")
    val context = LocalContext.current


    LazyVerticalGrid(
        columns = GridCells.Fixed(3), // 3개의 열
        //contentPadding = PaddingValues(4.dp) // 그리드 내용의 패딩
    ) {
        item{
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .border(width = 1.dp, color = Color.Black), contentAlignment = Alignment.Center){
                Text("카메라(미구현)")
            }
        }
        itemsIndexed(galleryImages) {index,item->
            val painter = rememberImagePainter(item.uri)
            Box(modifier = Modifier
                .height(140.dp)
                .border(width = 1.dp, color = Color.Black)
                .clickable {
                    try {
                        CoroutineScope(Dispatchers.IO).launch {
                             viewModel.uploadImages(listOf(item.uri), context)
                        }
                    } catch (e: HttpException) {
                        Log.d("error", "uri failed")
                    }
                    viewModel.updateIndex(index)
                }){
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(1.dp)
                        .border(
                            width = 2.dp,
                            color = if (item.isSelected) Color(0xFFFFA500) else Color.Transparent,
                        ), // 예시 크기,
                    contentScale =  ContentScale.Crop
                )
                Box(
                    Modifier
                        .size(32.dp)
                        .align(Alignment.TopEnd)
                        .padding(top = 8.dp, end = 8.dp)
                        .clip(CircleShape)
                        .border(
                            width = 2.dp,
                            color = if (item.isSelected) Color.Transparent else Color.White,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ){
                    if(item.isSelected){
                        Log.d("aaaa","changed")
                        Box( modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color(0xFFFFA500)))
                    }
                }

            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryViewProfilePageToolBar(selectedImage: String, viewModel: MainViewModel, navController: NavController){
    TopAppBar(

        title = { Text(text = "모든 사진") },
        navigationIcon = {
            Row{
                BackButton(navController = navController)
            }
        },
        actions = {

            Box(modifier = Modifier
                .fillMaxHeight()
                .width(100.dp)
                .then(
                    if (selectedImage.isNotEmpty()) {
                        Modifier.clickable {

                            viewModel.updateProfileImage(selectedImage)
                            Log.d(
                                "abcd",
                                navController.currentBackStack.value
                                    .map { it.destination.route }
                                    .toString()
                            )

                            navController.popBackStack()
                        }
                    } else Modifier
                ),
                contentAlignment = Alignment.CenterEnd){
                Text("완료", color = if(selectedImage.isEmpty()) Color.Gray else Color.Black)
            }
        },

        )
}