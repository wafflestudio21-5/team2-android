package com.wafflestudio.bunnybunny.pages

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.wafflestudio.bunnybunny.components.compose.BackButton
import com.wafflestudio.bunnybunny.components.compose.HomeButton
import com.wafflestudio.bunnybunny.components.compose.ImagePager
import com.wafflestudio.bunnybunny.components.compose.MoreVertButton
import com.wafflestudio.bunnybunny.components.compose.ShareButton
import com.wafflestudio.bunnybunny.lib.network.dto.GoodsPostContent
import com.wafflestudio.bunnybunny.viewModel.MainViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun GoodsPostPage(viewModel: MainViewModel,id:Long,navController: NavController){
    val goodsPostContent by viewModel.goodsPostContent.collectAsState()
    Log.d("aaaa",id.toString()+" and "+goodsPostContent.id.toString())
    if(id!=goodsPostContent.id&&!viewModel.isgettingNewPostContent){
        viewModel.isgettingNewPostContent=true
        //api로 새로운 게시물 받아와서 viewModel에 저장

        viewModel.getGoodsPostContent(id)
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
    Scaffold(bottomBar = { GoodsPostBottomBar(viewModel,goodsPostContent,navController) }, topBar = {GoodsPostToolbar(alpha = alpha.value, navController = navController) }) {paddingValues->

        LazyColumn(state = listState, modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding())){
            item{
                ImagePager(images = goodsPostContent.images, modifier = Modifier
                    .fillMaxWidth()
                    .height(imgHeight.dp))
            }
            item{
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(start = 16.dp, end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    val painter = rememberImagePainter(data = goodsPostContent.repImg)
                    Image(
                        painter = painter,
                        contentDescription = null,
                        modifier = Modifier
                            .size(60.dp)
                            .clip(shape = CircleShape),
                        contentScale =  ContentScale.Crop
                    )
                    Column{
                        Text(text=goodsPostContent.authorName)
                        Text(text=goodsPostContent.sellingArea)

                    }
                }
                Column (modifier = Modifier.padding(start = 16.dp, end = 16.dp)){
                    Divider(modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth())
                    Text(text=goodsPostContent.title, fontSize = 30.sp)
                    Text(text=goodsPostContent.refreshedAt.toString(), fontSize = 15.sp)
                    Text(text=goodsPostContent.description, fontSize = 20.sp, lineHeight = 20.sp)
                    Text(text=(if(goodsPostContent.chatCnt>0)
                        "채팅 ${goodsPostContent.chatCnt}·" else "")+
                            (if(goodsPostContent.wishCnt>0)
                                "관심 ${goodsPostContent.wishCnt}·" else "")+
                            "조회 ${goodsPostContent.viewCnt}",
                        fontSize = 15.sp)
                }

            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoodsPostToolbar(alpha:Float,navController: NavController) {
    val interpolatedColor = lerp(Color.White, Color.Black, alpha)
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
            containerColor = Color.White.copy(alpha = alpha),
            navigationIconContentColor = interpolatedColor,
            titleContentColor = interpolatedColor, // Color for the title
            actionIconContentColor = interpolatedColor // Color for action icons
        ),
    )

}
@Composable
fun GoodsPostBottomBar(viewModel: MainViewModel, goodsPostContent:GoodsPostContent,navController: NavController){
    Divider(modifier = Modifier
        .height(1.dp)
        .fillMaxWidth())
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(80.dp)
        .padding(top = 16.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically){
        IconButton(onClick = {
            viewModel.wishToggle(goodsPostContent.id,!goodsPostContent.isWish)
            Log.d("aaaa",(!goodsPostContent.isWish).toString())
            viewModel.updateGoodsPostContent(goodsPostContent.copy(isWish = !goodsPostContent.isWish,wishCnt=goodsPostContent.wishCnt+if(goodsPostContent.isWish)-1 else 1))            //api 통해서 wish 변화를 서버로 전달

        }) {
            Icon(
                imageVector = if(goodsPostContent.isWish)Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                contentDescription = "Wish"
            )
        }
        Divider(modifier = Modifier
            .width(1.dp)
            .fillMaxHeight())
        Column(Modifier.padding(start = 16.dp),verticalArrangement = Arrangement.Center){
            Text("${goodsPostContent.sellPrice}원", fontSize = 20.sp)
            Text(if(goodsPostContent.offerYn)"가격 제안 가능" else "가격 제안 불가", fontSize = 15.sp)
        }
        Spacer(modifier = Modifier.weight(1f))
        Box(modifier = Modifier.padding(end = 16.dp).height(50.dp).width(100.dp).clip(shape= RoundedCornerShape(4.dp)).background(color = MaterialTheme.colorScheme.secondary).clickable {
            //채팅 화면으로 이동
        }, contentAlignment = Alignment.Center){
            Text("채팅하기")
        }

    }
}
