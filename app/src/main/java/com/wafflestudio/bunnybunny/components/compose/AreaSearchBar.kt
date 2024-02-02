package com.wafflestudio.bunnybunny.components.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wafflestudio.bunnybunny.components.UI.bunnyColor
import com.wafflestudio.bunnybunny.data.example.SimpleAreaData
import com.wafflestudio.bunnybunny.viewModel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AreaSearchBar(modifier: Modifier, areaDetails: List<SimpleAreaData>) {
    var text by remember { mutableStateOf("") }
    var searchTarget by remember {
        mutableStateOf("")
    }
    val coroutineScope = rememberCoroutineScope()
    val viewModel = hiltViewModel<MainViewModel>()

    Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
        BasicTextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = { text = it },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done // 엔터 키를 Done으로 설정합니다.
            ),
            keyboardActions = KeyboardActions(onDone = {
                coroutineScope.launch(Dispatchers.IO) {
                    try {
                        viewModel.tryAreaSearch(text, 0);
                        searchTarget = text
                    } catch (_: Exception) {
                    }
                }
            }),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .background(
                            color = Color(0xFFF6F7F9),
                            shape = RoundedCornerShape(percent = 20)
                        )
                        .padding(12.dp),
                ) {
                    innerTextField()
                    if (text.isEmpty()) {
                        Text("내 동네 이름(동,읍,면)으로 검색", color = Color(0xFFDDDFE2))
                    } else {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Image(
                                modifier = Modifier.clickable { text = "" },
                                imageVector = Icons.Default.Cancel,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        )
        if (searchTarget.isNotBlank()) {
            Text(
                text = "'$searchTarget' 검색결과",
                fontWeight = FontWeight.Bold
            )
            if (areaDetails.isEmpty()) {
                Column(
                    modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(text = "검색 결과 없어요.", color = Color.Gray)
                    Text(text = "동네 이름을 다시 확인해주세요!", color = Color.Gray)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "동네 이름 다시 검색하기", color = bunnyColor, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}


