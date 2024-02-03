package com.wafflestudio.bunnybunny.pages

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.wafflestudio.bunnybunny.components.UI.bunnyColor
import com.wafflestudio.bunnybunny.components.compose.BackButton
import com.wafflestudio.bunnybunny.components.compose.BunnyButton
import com.wafflestudio.bunnybunny.components.compose.LoginInputTextField
import com.wafflestudio.bunnybunny.viewModel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuctionPage(viewModel: MainViewModel, navController: NavController, id: Long, maxPrice: Int){
    Log.d("aaaa","auction page")
    var newPrice by rememberSaveable { mutableStateOf(maxPrice.toString()) }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier,
                title = {Text("경매")},
                navigationIcon = {
                    Row{
                        BackButton(navController = navController)
                    }
                },
            )
        }
    ){paddingValues ->
        Divider(
            Modifier
                .fillMaxWidth()
                .height(1.dp)
        )
        Column(modifier = Modifier
            .padding(paddingValues)
            .padding(20.dp)
            .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            Box(
                modifier = Modifier.padding(10.dp)
            ) {
                Text("최고가: $maxPrice", fontWeight = FontWeight.Bold)
            }
            LoginInputTextField(value = newPrice,
                onValueChange = {newText -> newPrice = newText},
                placeholder = newPrice)
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .size(100.dp, 40.dp)
                    .clip(shape = RoundedCornerShape(20))
                    .background(color = bunnyColor)
                    .clickable {
                        Log.d("aaaaa", "마지막 발악1")

                        try {
                            Log.d("aaaaa", "마지막 발악2")
                            CoroutineScope(Dispatchers.IO).launch {
                                Log.d("aaaaa", "마지막 발악3")

                                //viewModel.postAuction(id, newPrice.toInt())
                            }
                        } catch (e: HttpException) {
                            Log.d("aaaa", "Can't bid price")
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text("완료", color = Color.Yellow)
            }
        }
    }




}