package com.wafflestudio.bunnybunny.pages

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.wafflestudio.bunnybunny.components.compose.AreaTextButton
import com.wafflestudio.bunnybunny.components.compose.DistanceSlider
import com.wafflestudio.bunnybunny.viewModel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun AreaSettingPage(viewModel: MainViewModel, navController: NavController) {

    val goldenRatio = 1.618f

    var firstClicked by remember { mutableStateOf(true) }
    var secondClicked by remember { mutableStateOf(false) }

    val currentRefAreaId by viewModel.currentRefAreaId.collectAsState()

    var firstAreaName = viewModel.firstName.collectAsState()
    var secondAreaName = viewModel.secondName.collectAsState()

    LaunchedEffect(currentRefAreaId) {
        // 비동기로 지역명 가져오는 함수 (예시로 delay 사용)
        viewModel.fetchAreaName(currentRefAreaId[0])
        if (currentRefAreaId.size == 2) {
            viewModel.fetchAreaName(currentRefAreaId[1])
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column {
                AreaTextButton(
                    modifier = Modifier
                        .height(100.dp) // 예시로 100dp로 설정
                        .width((100.dp * goldenRatio))
                        .padding(8.dp)
                        .border(
                            width = 1.dp,
                            color = Color(0xFFE87111),
                            shape = RoundedCornerShape(percent = 20)),
                    color = if (firstClicked) ClickedButtonColors() else NotClickedButtonColors(),
                    text = firstAreaName.value,
                    onClick = {
                        if (!firstClicked) {
                            firstClicked = !firstClicked
                            secondClicked = !secondClicked
                        }
                    }
                )

                AreaTextButton(
                    modifier = Modifier
                        .height(80.dp) // 예시로 100dp로 설정
                        .width((100.dp * goldenRatio))
                        .padding(8.dp)
                        .border(
                            width = 1.dp,
                            color = Color(0xFFE87111),
                            shape = RoundedCornerShape(percent = 20)),
                    color = if (firstClicked) ClickedButtonColors() else NotClickedButtonColors(),
                    text = "지역 삭제하기",
                    onClick = {
                        if (!firstClicked) {
                            CoroutineScope(Dispatchers.IO).launch {
                                if (currentRefAreaId.size != 1) {
                                    viewModel.deleteRefAreaId(currentRefAreaId[0])
                                }
                            }
                        }


                    }
                )
            }

            Spacer(modifier = Modifier.width(16.dp)) // 간격 조절

            if (currentRefAreaId.size == 2) {
                Column {
                    AreaTextButton(
                        modifier = Modifier
                            .height(100.dp) // 예시로 100dp로 설정
                            .width((100.dp * goldenRatio)) // 가로 크기의 1배, 세로 크기의 1.618배
                            .padding(8.dp)
                            .border(
                                width = 1.dp,
                                color = Color(0xFFE87111),
                                shape = RoundedCornerShape(percent = 20)),

                        color = if (secondClicked) ClickedButtonColors() else NotClickedButtonColors(),
                        text = secondAreaName.value,
                        onClick = {
                            if (!secondClicked) {
                                firstClicked = !firstClicked
                                secondClicked = !secondClicked
                            }

                        }
                    )

                    AreaTextButton(
                        modifier = Modifier
                            .height(80.dp) // 예시로 100dp로 설정
                            .width((100.dp * goldenRatio))
                            .padding(8.dp)
                            .border(
                                width = 1.dp,
                                color = Color(0xFFE87111),
                                shape = RoundedCornerShape(percent = 20)),
                        color = if (secondClicked) ClickedButtonColors() else NotClickedButtonColors(),
                        text = "지역 삭제하기",
                        onClick = {
                            CoroutineScope(Dispatchers.IO).launch {
                                Log.d("ASP", "지역이 삭제되었습니다.")
                                viewModel.deleteRefAreaId(currentRefAreaId[1])
                                Log.d("ASP", "$currentRefAreaId")
                            }
                        }
                    )
                }

            } else if (currentRefAreaId.size == 1) {

                Column {
                    AreaTextButton(
                        modifier = Modifier
                            .height(100.dp) // 예시로 100dp로 설정
                            .width((100.dp * goldenRatio)) // 가로 크기의 1배, 세로 크기의 1.618배
                            .padding(8.dp)
                            .border(
                                width = 1.dp,
                                color = Color(0xFFE87111),
                                shape = RoundedCornerShape(percent = 20)),

                        color = if (secondClicked) ClickedButtonColors() else NotClickedButtonColors(),
                        text = " + ",
                        onClick = {
                            navController.navigate("AreaChangePage")
                        }
                    )
                }


            }
        }


        Row(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()) {
            DistanceSlider()
        }
    }

}

@Composable
fun ClickedButtonColors(): ButtonColors {

    return ButtonDefaults.buttonColors(
        containerColor = Color(0xFFE87111), // 주황
        contentColor = Color.White, // 하양
        disabledContainerColor = Color.Gray,
        disabledContentColor = Color.White
    )
}

@Composable
fun NotClickedButtonColors(): ButtonColors {

    return ButtonDefaults.buttonColors(
        containerColor = Color.White, // 주황
        contentColor = Color(0xFFE87111), // 하양
        disabledContainerColor = Color.Gray,
        disabledContentColor = Color.White
    )
}