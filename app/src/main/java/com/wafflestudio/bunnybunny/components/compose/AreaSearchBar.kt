package com.wafflestudio.bunnybunny.components.compose

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.hilt.navigation.compose.hiltViewModel
import com.wafflestudio.bunnybunny.viewModel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AreaSearchBar(modifier: Modifier) {
    var text by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val viewModel = hiltViewModel<MainViewModel>()

    Row (modifier = Modifier.fillMaxWidth()) {
        TextField (modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = { text = it },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done // 엔터 키를 Done으로 설정합니다.
            ),
            keyboardActions = KeyboardActions(onDone = {
                try {
                    coroutineScope.launch(Dispatchers.IO) {
                        viewModel.tryAreaSearch(text, 0);
                    }
                } catch (e: HttpException) {

                }

            }),
            leadingIcon = {
                Icon (
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    tint = Color.Gray
                )
            }
        )
    }



}