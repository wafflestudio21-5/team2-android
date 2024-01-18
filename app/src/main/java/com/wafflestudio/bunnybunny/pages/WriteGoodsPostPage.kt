package com.wafflestudio.bunnybunny.pages

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.wafflestudio.bunnybunny.components.compose.BackButton
import com.wafflestudio.bunnybunny.components.compose.HomeButton
import com.wafflestudio.bunnybunny.components.compose.MoreVertButton
import com.wafflestudio.bunnybunny.components.compose.ShareButton
import com.wafflestudio.bunnybunny.lib.network.dto.SubmitPostRequest
import com.wafflestudio.bunnybunny.viewModel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WriteGoodsPostPage(viewModel: MainViewModel,navController: NavController){

    var title by remember { mutableStateOf("") }
    var isTitleFocused by remember { mutableStateOf(false) }
    val titleScrollState = rememberScrollState()
    var sellPrice by remember { mutableStateOf("") }
    var isSellPriceFocused by remember { mutableStateOf(false) }
    var offerYn by remember { mutableStateOf(false) }
    var description by remember { mutableStateOf("") }
    var isDescriptionFocused by remember { mutableStateOf(false) }
    val localFocusManager = LocalFocusManager.current

    Scaffold(bottomBar = {
        Box (modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .clip(RoundedCornerShape(5.dp))
            .border(width = 2.dp, color = Color.Gray, shape = RoundedCornerShape(7.dp))
            .clickable {
                if (title.isNotEmpty() && sellPrice.isNotEmpty() && description.isNotEmpty()) {
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            viewModel.submitPost(
                                SubmitPostRequest(
                                    areaId = viewModel.refAreaId[0],
                                    title = title,
                                    description = description,
                                    type = if (sellPrice.toInt() == 0) "SHARE" else "TRADE",
                                    images = listOf(),
                                    deadline = 0L,
                                    offerYn = offerYn,
                                    sellPrice = sellPrice.toInt()
                                )
                            )
                            withContext(Dispatchers.Main) {
                                //게시글 작성에 성공.
                                // 내가 쓴 글 페이지로 이동(현재 페이지를 stack에서 지우면서)
                            }
                        } catch (e: Exception) {
                            //
                            Log.d("submitpost", "submitpost failed:$e")
                        }
                    }
                }
            },
            contentAlignment = Alignment.Center){
            Text(text = "작성 완료", textAlign = TextAlign.Center)
        }
    }){paddingValues->
        LazyColumn(Modifier.padding(paddingValues)){
            item{
                TopAppBar(

                    title = {Text("당근")},
                    navigationIcon = {
                        Row{
                            BackButton(navController = navController)
                        }
                    },
                    actions = {
                        Text("임시저장")
                    },
                )
                Divider(
                    Modifier
                        .fillMaxWidth()
                        .height(1.dp))
                Column(Modifier.padding(16.dp)){
                    Row {
                        Box(
                            Modifier
                                .size(80.dp)
                                .clip(RoundedCornerShape(5.dp))
                                .border(
                                    width = 2.dp,
                                    color = Color.Gray,
                                    shape = RoundedCornerShape(7.dp)
                                )
                                .clickable {
                                    //사진 첨부
                                },
                            contentAlignment = Alignment.Center
                        ){
                            Column(horizontalAlignment = Alignment.CenterHorizontally){
                                Icon(
                                    imageVector = Icons.Outlined.CameraAlt,
                                    contentDescription = "CameraAlt"
                                )
                                Text("0/10")
                            }
                        }
                    }

                    Spacer(modifier = Modifier.fillMaxWidth().height(40.dp))

                    Text("제목")
                    Spacer(modifier = Modifier.fillMaxWidth().height(20.dp))
                    BasicTextField(
                        value = title,
                        onValueChange = { title = it},
                        modifier = Modifier.onFocusChanged { focusState ->
                            isTitleFocused = focusState.isFocused
                        },
                        singleLine=true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { localFocusManager.moveFocus(FocusDirection.Down) }
                        ),
                        decorationBox = {
                                innerTextField ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(max = 45.dp)
                                    .clip(RoundedCornerShape(5.dp))
                                    .border(
                                        width = 2.dp,
                                        color = if (isTitleFocused) Color.Black else Color.Gray,
                                        shape = RoundedCornerShape(7.dp)
                                    )
                                    .padding(12.dp)
                                    .horizontalScroll(titleScrollState),
                            ){
                                if(title.isEmpty()){
                                    Text("제목", color = Color.Gray)
                                }
                                innerTextField()
                            }
                        }
                    )
                    LaunchedEffect(title) {
                        titleScrollState.scrollTo(titleScrollState.maxValue)
                    }
                    Spacer(modifier = Modifier.fillMaxWidth().height(40.dp))

                    Text("거래 방식")
                    Spacer(modifier = Modifier.fillMaxWidth().height(20.dp))
                    BasicTextField(
                        value = sellPrice,
                        onValueChange = {newText->
                            if (newText.all { it.isDigit() }&&newText.length<10) {
                                sellPrice = newText
                            }

                        },
                        modifier = Modifier.onFocusChanged { focusState ->
                            isSellPriceFocused = focusState.isFocused
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Number,
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { localFocusManager.moveFocus(FocusDirection.Down) }
                        ),
                        decorationBox = {
                                innerTextField ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(5.dp))
                                    .border(
                                        width = 2.dp,
                                        color = if (isSellPriceFocused) Color.Black else Color.Gray,
                                        shape = RoundedCornerShape(7.dp)
                                    )
                                    .padding(12.dp),
                            ){
                                Row {
                                    Text("￦")
                                    Box{
                                        if(sellPrice.isEmpty()){
                                            Text("가격을 입력해주세요.", color = Color.Gray)
                                        }
                                        innerTextField()
                                    }
                                }

                            }
                        }
                    )
                    Spacer(modifier = Modifier.fillMaxWidth().height(20.dp))
                    Row(verticalAlignment = Alignment.CenterVertically){
                        Checkbox(
                            checked = offerYn,
                            onCheckedChange = { offerYn = it }
                        )
                        Box(contentAlignment = Alignment.Center){Text("가격 제안 받기")}
                    }

                    Spacer(modifier = Modifier.fillMaxWidth().height(40.dp))

                    Text("자세한 설명")
                    Spacer(modifier = Modifier.fillMaxWidth().height(20.dp))
                    BasicTextField(
                        value = description,
                        onValueChange = { description = it},
                        modifier = Modifier.onFocusChanged { focusState ->
                            isDescriptionFocused = focusState.isFocused
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            //
                        ),
                        keyboardActions = KeyboardActions(
                            //
                        ),
                        decorationBox = {
                                innerTextField ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(min=160.dp)
                                    .clip(RoundedCornerShape(5.dp))
                                    .border(
                                        width = 2.dp,
                                        color = if (isDescriptionFocused) Color.Black else Color.Gray,
                                        shape = RoundedCornerShape(7.dp)
                                    )
                                    .padding(12.dp),
                            ){
                                if(description.isEmpty()){
                                    Text("(지역명)에 올릴 게시글 내용을 작성해 주세요.\n" +
                                            "(판매 금지 물품은 게시가 제한될 수 있어요.)\n" +
                                            "\n" +
                                            "신뢰할 수 있는 거래를 위해 자세히 적어주세요.\n" +
                                            "과학기술정보통신부, 한국 인터넷진흥원과 함께해요.", color = Color.Gray)
                                }
                                innerTextField()
                            }
                        }
                    )
                }
            }
        }
    }
}
