package com.wafflestudio.bunnybunny.pages

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.wafflestudio.bunnybunny.components.compose.BunnyButton
import com.wafflestudio.bunnybunny.components.compose.LoginInputTextField
import com.wafflestudio.bunnybunny.viewModel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

@Composable
fun AuctionPage(viewModel: MainViewModel, navController: NavController, id: Long, maxPrice: Int){
    Log.d("aaaa","auction page")
    var newPrice by rememberSaveable { mutableStateOf(maxPrice.toString()) }
    Text(maxPrice.toString())
    LoginInputTextField(value = newPrice,
        onValueChange = {newText -> newPrice = newText},
        placeholder = newPrice)
    BunnyButton(
        onClick = {
            try {
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.postBidPrice(id, newPrice.toInt())
                }
            } catch(e: HttpException){
                Log.d("aaaa", "Can't bid price")
            }
        }, text = "완료")


}