package com.wafflestudio.bunnybunny.pages

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.wafflestudio.bunnybunny.components.compose.BackButton
import com.wafflestudio.bunnybunny.viewModel.MainViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
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

    val (postSearched, setPostSearched) = remember { mutableStateOf(false) }

    LaunchedEffect(postSearched) {
        if (postSearched) {
            viewModel.updateSearchPostList(
                distance = 0,
                areaId = viewModel.getRefAreaId()[0],
                count=0,
                cur=null,
                seed=null
            )

            setPostSearched(false) // 상태를 다시 초기화
        }
    }

    Scaffold (topBar = {
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
                        }
                    ),
                    decorationBox = {
                            innerTextField ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 45.dp)
                                .clip(RoundedCornerShape(5.dp))
                                .padding(12.dp)
                                .horizontalScroll(scrollState),
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
    }){

    }
}
