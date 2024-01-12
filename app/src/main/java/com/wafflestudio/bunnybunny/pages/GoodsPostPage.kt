package com.wafflestudio.bunnybunny.pages

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.wafflestudio.bunnybunny.components.compose.BackButton
import com.wafflestudio.bunnybunny.components.compose.HomeButton
import com.wafflestudio.bunnybunny.components.compose.ImagePager
import com.wafflestudio.bunnybunny.components.compose.MoreVertButton
import com.wafflestudio.bunnybunny.components.compose.ShareButton
import com.wafflestudio.bunnybunny.viewModel.MainViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun GoodsPostPage(id:Long, viewModel: MainViewModel,navController: NavController){
    if(id!=viewModel.goodsPostContent.value.id){
        //api로 새로운 게시물 받아와서 viewModel에 저장
    }
    val listState = rememberLazyListState()
    val imgHeight=400
    val alpha= remember { derivedStateOf {
        if(listState.layoutInfo.visibleItemsInfo.firstOrNull()==null)
            0f
        else if(listState.firstVisibleItemIndex==0)
                (listState.firstVisibleItemScrollOffset.toFloat()/listState.layoutInfo.visibleItemsInfo.firstOrNull()!!.size)
        else 1f
    }}

    //dpToPixel(imgHeight.toFloat(),)
    /*
    val alpha by remember {
        derivedStateOf {
            // Calculate the alpha based on the scroll offset
            // Coerce the value to be between 0f and 1f
            (
                    //1f-(listState.layoutInfo.visibleItemsInfo.firstOrNull()?.size?:0)/imgHeight

            ).toFloat()//.coerceIn(0f, 1f)
        }
    }*/
    Scaffold(bottomBar = {  }, topBar = {ToolbarWithMenu(alpha = alpha.value, navController = navController) }) {

        LazyColumn(state = listState){
            item{
                ImagePager(images = viewModel.goodsPostContent.value.images, modifier = Modifier
                    .fillMaxWidth()
                    .height(imgHeight.dp))
            }
            item {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(color = MaterialTheme.colorScheme.secondary),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    val painter = rememberImagePainter(data = viewModel.goodsPostContent.value.repImg)
                    Image(
                        painter = painter,
                        contentDescription = null,
                        modifier = Modifier
                            .size(60.dp).clip(shape = CircleShape),
                        contentScale =  ContentScale.Crop
                    )
                    Column{
                        Text(text=viewModel.goodsPostContent.value.authorId.toString())
                        Text(text=viewModel.goodsPostContent.value.sellingArea)

                    }
                }
                Divider(modifier = Modifier.width(1.dp))
                Text(text=viewModel.goodsPostContent.value.title, fontSize = 50.sp)
                Text(text=viewModel.goodsPostContent.value.refreshedAt.toString(), fontSize = 50.sp)
                Text(text=viewModel.goodsPostContent.value.description, fontSize = 50.sp, lineHeight = 50.sp)
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolbarWithMenu(alpha:Float,navController: NavController) {
    TopAppBar(
        title = { },
        navigationIcon = {
            Row{
                BackButton(navController = navController)
                HomeButton(navController = navController)
            }
        },
        actions = {
            ShareButton()
            MoreVertButton()
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = alpha),
            navigationIconContentColor = Color.White,
            titleContentColor = Color.White, // Color for the title
            actionIconContentColor = Color.White // Color for action icons
        ),
    )

}
