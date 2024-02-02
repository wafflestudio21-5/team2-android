package com.wafflestudio.bunnybunny.components.compose

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.wafflestudio.bunnybunny.data.example.RefAreaId
import com.wafflestudio.bunnybunny.data.example.SimpleAreaData
import com.wafflestudio.bunnybunny.viewModel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun AreaFixedBox(modifier: Modifier, areaDetail: SimpleAreaData, areaIds: MutableList<Int>, navController: NavController) {

    val areaId = areaDetail.id
    val context = LocalContext.current
    var isClicked by remember { mutableStateOf(false) }
    val viewModel: MainViewModel = hiltViewModel()


    Box(
        modifier = modifier
            .background(if ((isClicked) && (areaIds.contains(areaId))) Color.Gray else Color.White)
            .clickable {
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.addRefAreaId(areaId)
                    navController.popBackStack()
                }
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .drawWithContent {
                    drawContent()
                    drawBorderBottom(size, 1.dp, Color.LightGray)
                }
                .padding(16.dp)
        ) {
            Text(
                text = areaDetail.fullName,
                fontSize = 16.sp,
                color = Color.Black
            )
        }
    }
}