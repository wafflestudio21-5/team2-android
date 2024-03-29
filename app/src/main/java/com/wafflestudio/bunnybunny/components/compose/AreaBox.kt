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
import androidx.navigation.NavController
import com.wafflestudio.bunnybunny.data.example.RefAreaId
import com.wafflestudio.bunnybunny.data.example.SimpleAreaData
import com.wafflestudio.bunnybunny.pages.RefArea
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun AreaBox(modifier: Modifier, areaDetail: SimpleAreaData, areaIds: MutableList<RefArea>) {
    val areaId = areaDetail.id
    val context = LocalContext.current
    var isClicked by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .background(if ((isClicked)&&(areaIds.map { it.id }.contains(areaId))) Color.LightGray else Color.White)
            .clickable {
                if ((!isClicked)&&(areaIds.size == 2)) {
                    CoroutineScope(Dispatchers.Main).launch() {
                        Toast.makeText(context, "최대 2개의 지역만 선택 가능합니다.", Toast.LENGTH_SHORT).show()
                    }
                } else if ((isClicked)&&(areaIds.map { it.id }.contains(areaId))) {
                    areaIds.removeIf { it.id == areaId }
                    isClicked = !isClicked
                } else if (!areaIds.map { it.id }.contains(areaId)) {
                    areaIds.add(RefArea(areaId, areaDetail.name))
                    isClicked = !isClicked
                }
                Log.d("AreaBox", "$areaIds")
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

fun DrawScope.drawBorderBottom(size: Size, thickness: Dp, color: Color) {
    drawRect(
        color = color,
        topLeft = Offset(0f, size.height - thickness.toPx()),
        size = Size(size.width, thickness.toPx())
    )
}