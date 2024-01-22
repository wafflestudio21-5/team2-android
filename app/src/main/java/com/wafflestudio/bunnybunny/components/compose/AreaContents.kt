package com.wafflestudio.bunnybunny.components.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.wafflestudio.bunnybunny.viewModel.MainViewModel

@Composable
fun AreaContents(modifier: Modifier, refAreaIds: MutableList<Int>) {
    val viewModel: MainViewModel = hiltViewModel()
    val areaDetails by viewModel.areaDetails.collectAsState()


    LazyColumn (modifier = Modifier.fillMaxWidth()) {
        items(areaDetails.size) { position ->
            AreaBox(modifier = Modifier,
                areaDetail = areaDetails[position],
                areaIds = refAreaIds)
        }
    }

}