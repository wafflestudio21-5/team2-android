package com.wafflestudio.bunnybunny.components.compose

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.wafflestudio.bunnybunny.viewModel.MainViewModel


@Composable
fun CurrentAreaWithDropDownMenu(viewModel: MainViewModel, navController: NavController) {

    var isDropDownMenuExpanded by remember { mutableStateOf (false)}
    val currentRefAreaId by viewModel.currentRefAreaId.collectAsState()

    var firstAreaName = viewModel.firstName.collectAsState()
    var secondAreaName = viewModel.secondName.collectAsState()

    LaunchedEffect(currentRefAreaId) {
        // 비동기로 지역명 가져오는 함수 (예시로 delay 사용)
        if (currentRefAreaId.size > 0) {
            viewModel.fetchAreaName(currentRefAreaId[0])
        }
        if (currentRefAreaId.size == 2) {
            viewModel.fetchAreaName(currentRefAreaId[1])
        }
    }

    Column (modifier = Modifier.clickable {
        isDropDownMenuExpanded = true
    }) {
        if (viewModel.getRefAreaId().size == 2) {

            Text(modifier = Modifier,
                text = firstAreaName.value)

            DropdownMenu (
                modifier = Modifier.wrapContentSize(),
                expanded = isDropDownMenuExpanded,
                onDismissRequest = { isDropDownMenuExpanded = false }
            ) {
                DropdownMenuItem(
                    onClick = {
                        Log.d("CAWDD", "clicked")
                    },
                    text = { DropDownText(modifier = Modifier, text = firstAreaName.value)
                        }
                )
                DropdownMenuItem(
                    onClick = {
                        // 오직 2번 항목이 있을
                        viewModel.swapRefAreaIdValues()
                    },
                    text = { DropDownText(modifier = Modifier, text = secondAreaName.value)}
                )
                DropdownMenuItem(
                    onClick = {
                        navController.navigate("AreaSettingPage")
                    },
                    text = { DropDownText(modifier = Modifier, text = "지역 설정") }
                )
            }
        } else if (viewModel.getRefAreaId().size == 1) {
            Text(modifier = Modifier
                .clickable{ navController.navigate("AreaSettingPage") },
                text = firstAreaName.value)
        }
    }


}