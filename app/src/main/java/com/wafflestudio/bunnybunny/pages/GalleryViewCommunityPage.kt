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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.wafflestudio.bunnybunny.components.compose.BackButton
import com.wafflestudio.bunnybunny.model.ToggleImageItem
import com.wafflestudio.bunnybunny.viewModel.CommunityViewModel
import com.wafflestudio.bunnybunny.viewModel.MainViewModel
import java.io.File

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryViewCommunityPage(viewModel: CommunityViewModel) {
    val galleryImages by viewModel.galleryImages.collectAsState()
    val selectedImages by viewModel.selectedImages.collectAsState()
    Scaffold(topBar = {GalleryViewCommunityPageToolBar(selectedImages,viewModel)}){paddingValues->
        Column(modifier = Modifier.padding(paddingValues)){
            GalleryViewCommunity(galleryImages, selectedImages, viewModel)
        }
    }

}


@Composable
fun GalleryViewCommunity(galleryImages:List<ToggleImageItem>,selectedImages:List<Int>,viewModel: CommunityViewModel) {
    Log.d("aaaa","GalleryView called")



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
                    val updatedList = selectedImages.toMutableList()
                    if (!item.isSelected) {
                        updatedList.add(index)
                    } else {
                        updatedList.remove(index)
                    }
                    viewModel.updateSelectedImages(updatedList)
                    Log.d("aaaa", viewModel.galleryImages.value.toString())
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
                        Text(text=item.selectedOrder.toString(), modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color(0xFFFFA500)), color = Color.White, textAlign = TextAlign.Center)
                    }
                }

            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryViewCommunityPageToolBar(selectedImages:List<Int>,viewModel: CommunityViewModel){
    TopAppBar(

        title = { Text(text = "모든 사진")},
        navigationIcon = {
            Row{
                IconButton(onClick = { viewModel.selectPage(WritePage.Home)}) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBackIos,
                        contentDescription = "Back"
                    )
                }
            }
        },
        actions = {

            Box(modifier = Modifier
                .fillMaxHeight()
                .width(100.dp)
                .then(
                    if (selectedImages.isNotEmpty()) {
                        Modifier.clickable {
                            val updateList = viewModel.uploadImages.value.toMutableList()
                            selectedImages.forEach {
                                updateList.add(viewModel.galleryImages.value[it].uri)
                            }
                            viewModel.updateUploadImages(updateList)
                            viewModel.selectPage(WritePage.Home)
                        }
                    } else Modifier
                ),
                contentAlignment = Alignment.CenterEnd){
                Row {
                    if(selectedImages.isNotEmpty()){
                        Text(text = "${selectedImages.size}", color = Color(0xFFFFA500))
                    }
                    Text("완료", color = if(selectedImages.isEmpty()) Color.Gray else Color.Black)
                }
            }
        },

        )
}