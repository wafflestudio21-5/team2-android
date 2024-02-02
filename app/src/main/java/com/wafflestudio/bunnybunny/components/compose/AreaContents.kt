package com.wafflestudio.bunnybunny.components.compose

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.wafflestudio.bunnybunny.viewModel.MainViewModel

@Composable
fun AreaContents(modifier: Modifier, refAreaIds: MutableList<Int>, isAreaChanging: Boolean, navController: NavController) {
    val viewModel: MainViewModel = hiltViewModel()
    val areaDetails by viewModel.areaDetails.collectAsState()

    var refAreaNames = remember {  mutableStateListOf<String>() }

//     Function to update RefAreaNames
//    val updateRefAreaNames: (List<String>) -> Unit = { updatedList ->
//        refAreaNames = updatedList.toMutableList()
//        Log.d("AreaChoosePage", "Updated refAreaNames: $refAreaNames")
//    }

    if (!isAreaChanging) {
        LaunchedEffect(refAreaNames) {
            Log.d("AreaContents", "$refAreaNames")
        }

        Column(modifier = Modifier.fillMaxHeight(0.12f)){
            Text(modifier = Modifier.fillMaxHeight(), text = refAreaNames.joinToString("\n"))
        }


        LazyColumn (modifier = Modifier.fillMaxWidth()) {
            items(areaDetails.size) { position ->
                AreaBox(modifier = Modifier,
                    areaDetail = areaDetails[position],
                    areaIds = refAreaIds,
                    refAreaNames = refAreaNames)
            }
        }
    } else {
        Column(modifier = Modifier.fillMaxHeight(0.12f)) {
            Text(modifier = Modifier.fillMaxHeight(), text = refAreaNames.joinToString("\n"))
        }


        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(areaDetails.size) { position ->
                AreaFixedBox(
                    modifier = Modifier,
                    areaDetail = areaDetails[position],
                    areaIds = refAreaIds,
                    navController = navController
                )
            }
        }
    }
}