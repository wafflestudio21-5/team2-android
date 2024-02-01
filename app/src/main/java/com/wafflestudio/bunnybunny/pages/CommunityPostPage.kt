package com.wafflestudio.bunnybunny.pages

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.wafflestudio.bunnybunny.SampleData.DefaultCommunityPostContentSample
import com.wafflestudio.bunnybunny.components.compose.BackButton
import com.wafflestudio.bunnybunny.components.compose.MoreVertButton
import com.wafflestudio.bunnybunny.components.compose.NotificationsButton
import com.wafflestudio.bunnybunny.components.compose.ShareButton
import com.wafflestudio.bunnybunny.utils.formatProductTime
import com.wafflestudio.bunnybunny.viewModel.CommunityViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CommunityPostPage(id: Long, navController: NavController) {
    val viewModel = hiltViewModel<CommunityViewModel>()
    val communityPostContent by viewModel.communityPostContent.collectAsState()
    //Log.d("aaaa", id.toString() + " and " + communityPostContent.id.toString())
    LaunchedEffect(key1 = true) {
        viewModel.updateCommunityPostContent(DefaultCommunityPostContentSample)
        viewModel.getCommunityPostContent(id)
    }
    //val listState = rememberLazyListState()
    //val imgHeight = 400
    /*
    val alpha = remember {
        derivedStateOf {
            if (listState.layoutInfo.visibleItemsInfo.firstOrNull() == null)
                0f
            else if (listState.firstVisibleItemIndex == 0)
                (listState.firstVisibleItemScrollOffset.toFloat() / listState.layoutInfo.visibleItemsInfo.firstOrNull()!!.size)
            else 1f
        }
    }

    //dpToPixel(imgHeight.toFloat(),)

    val alpha by remember {
        derivedStateOf {
            // Calculate the alpha based on the scroll offset
            // Coerce the value to be between 0f and 1f
            (
                    //1f-(listState.layoutInfo.visibleItemsInfo.firstOrNull()?.size?:0)/imgHeight

            ).toFloat()//.coerceIn(0f, 1f)
        }
    }*/
    Scaffold(
        bottomBar = {  },
        topBar = { CommunityPostToolbar(navController = navController) }) { paddingValues ->
        LazyColumn(
            //state = listState,
            modifier = Modifier.padding(paddingValues)
        ) {
            item{
                Column(Modifier.padding(16.dp)){
                    Row(verticalAlignment = Alignment.CenterVertically){
                        val painter =
                            rememberImagePainter(data = if (communityPostContent.profileImg != ""&&communityPostContent.profileImg!=null) communityPostContent.profileImg else "https://d1unjqcospf8gs.cloudfront.net/assets/users/default_profile_80-c649f052a34ebc4eee35048815d8e4f73061bf74552558bb70e07133f25524f9.png")
                        Image(
                            painter = painter,
                            contentDescription =null,
                            modifier = Modifier
                                .size(60.dp)
                                .border(
                                    1.dp,
                                    Color.Gray.copy(alpha = 0.2f),
                                    shape = CircleShape
                                )
                                .clip(shape = CircleShape),
                            contentScale = ContentScale.Crop,
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(text = communityPostContent.authorName, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = communityPostContent.areaName+"·"+ formatProductTime(communityPostContent.community.createdAt,communityPostContent.community.createdAt), color = Color.Gray, fontSize = 13.sp)

                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = communityPostContent.community.title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(text = communityPostContent.community.description, fontSize = 20.sp, lineHeight = 30.sp)
                }
            }
            items(communityPostContent.community.images){
                Image(
                    painter = rememberImagePainter(data=it),
                    contentDescription =null,
                    modifier = Modifier
                        .fillMaxWidth().height(300.dp) ,// 이미지가 가로 방향으로 최대한 차지하도록 설정
                    contentScale = ContentScale.FillWidth
                )
                Spacer(modifier = Modifier.height(24.dp))

            }
            item{
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = communityPostContent.community.viewCnt.toString()+"명이 봤어요",
                    fontSize = 15.sp, color = Color.Gray,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityPostToolbar(navController: NavController) {


    Column{
        TopAppBar(
            title = { },
            navigationIcon = {
                Row {
                    BackButton(navController = navController)
                }
            },
            actions = {
                NotificationsButton(false)
                ShareButton()
                MoreVertButton()
            },


            )
        Divider(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
        )
    }

}

