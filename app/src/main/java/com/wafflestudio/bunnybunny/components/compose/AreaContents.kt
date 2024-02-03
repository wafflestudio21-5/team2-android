package com.wafflestudio.bunnybunny.components.compose

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.R
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.outlined.Close
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.wafflestudio.bunnybunny.data.example.SimpleAreaData
import com.wafflestudio.bunnybunny.pages.RefArea
import com.wafflestudio.bunnybunny.viewModel.MainViewModel

@Composable
fun AreaContents(modifier: Modifier, refAreaIds: MutableList<RefArea>, areaDetails: List<SimpleAreaData>, navController: NavController, isForChange: Boolean) {
    if (areaDetails.isNotEmpty()) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(areaDetails.size) { position ->
                if (!isForChange) {
                    AreaBox(
                        modifier = Modifier,
                        areaDetail = areaDetails[position],
                        areaIds = refAreaIds,
                    )
                } else {
                    AreaFixedBox(modifier = Modifier, areaDetail = areaDetails[position], areaIds = refAreaIds, navController = navController)
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(30.dp))
    Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
        refAreaIds.map { refArea ->
            Row(
                modifier = Modifier
                    .background(
                        shape = RoundedCornerShape(percent = 40),
                        color = Color(0xFFDDDFE2)
                    )
                    .padding(vertical = 10.dp, horizontal = 16.dp)
                    .clickable { refAreaIds.removeIf { it.id == refArea.id } },
            ){
                Text(
                    text = refArea.name
                )
                Image(imageVector = Icons.Outlined.Close, contentDescription = null, colorFilter = ColorFilter.tint(Color.Gray))
            }
        }
    }

}