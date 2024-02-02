package com.wafflestudio.bunnybunny.pages

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberImagePainter
import com.wafflestudio.bunnybunny.components.compose.BackButton
import com.wafflestudio.bunnybunny.utils.formatProductTime
import com.wafflestudio.bunnybunny.viewModel.MainViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchPage(navController: NavController){
    val viewModel= hiltViewModel<MainViewModel>()
    var keyword by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    //var sellPrice by rememberSaveable { mutableStateOf("") }
    //var isSellPriceFocused by remember { mutableStateOf(false) }
    //var offerYn by rememberSaveable { mutableStateOf(false) }
    //var description by rememberSaveable { mutableStateOf("") }
    //var isDescriptionFocused by remember { mutableStateOf(false) }
    val localFocusManager = LocalFocusManager.current

    val keyboardManager=LocalSoftwareKeyboardController.current
    val (postSearched, setPostSearched) = remember { mutableStateOf(false) }
    val itemList = viewModel.SearchPostList.collectAsLazyPagingItems()

    LaunchedEffect(postSearched) {
        if (postSearched) {
            viewModel.updateSearchPostList(
                distance = 0,
                areaId = viewModel.getRefAreaId()[0],
                keyword=keyword
            )

            setPostSearched(false) // 상태를 다시 초기화
        }
    }

    Scaffold (topBar = {
        Row{
            TopAppBar(
                title={
                    BasicTextField(
                        value = keyword,
                        onValueChange = { keyword = it},
                        singleLine=true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Search
                        ),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                setPostSearched(true)
                                keyboardManager?.hide()
                            }
                        ),
                        decorationBox = {
                                innerTextField ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(min=40.dp)
                                    .clip(RoundedCornerShape(5.dp))
                                    .background(color = Color(0xFF444444))
                                    .padding(horizontal = 4.dp)
                                    .horizontalScroll(scrollState),
                                contentAlignment = Alignment.CenterStart
                            ){
                                if(keyword.isEmpty()){
                                    Text("(지역명) 근처에서 검색", color = Color.Gray)
                                }
                                innerTextField()
                            }
                        }
                    )
                },
                navigationIcon = {
                    BackButton(navController = navController)
                },
            )
            Divider(modifier = Modifier.fillMaxWidth().height(1.dp), color = Color.Gray)
        }
        }){paddingValues->
        Log.d("aaaa123", "vcalled")



        LazyColumn(modifier = Modifier.padding(paddingValues)){
            item {
                //물품 필터
            }

            items(itemList){
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
                ){
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
                            Text(text = it.tradingLocation+"·"+ formatProductTime(it.createdAt, it.refreshedAt), color = Color.Gray, fontSize = 13.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = it.sellPrice.toString()+"원", fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(4.dp))
                            if (it.wishCnt > 0 || it.chatCnt > 0) {
                                Text(text = (if (it.wishCnt > 0) "관심 " + it.wishCnt.toString() else "") + (if (it.chatCnt > 0) "채팅 " + it.chatCnt.toString() else ""))
                            }
                        }
                    }
                }
                Divider(
                    color = Color.Gray.copy(alpha = 0.2f),
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                )
            }

        }
    }

}
