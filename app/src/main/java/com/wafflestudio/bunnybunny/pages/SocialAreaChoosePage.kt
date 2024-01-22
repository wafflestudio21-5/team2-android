package com.wafflestudio.bunnybunny.pages

import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.wafflestudio.bunnybunny.components.compose.AreaContents
import com.wafflestudio.bunnybunny.components.compose.AreaSearchBar
import com.wafflestudio.bunnybunny.components.compose.BasicButton
import com.wafflestudio.bunnybunny.data.example.SignupRequest
import com.wafflestudio.bunnybunny.data.example.SocialSignupRequest
import com.wafflestudio.bunnybunny.utils.mutableListToString
import com.wafflestudio.bunnybunny.utils.stringToMutableList
import com.wafflestudio.bunnybunny.viewModel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

@Composable
fun SocialAreaChoosePage(nickname: String, idToken: String) {
    val refAreaIds = mutableListOf<Int>()
    val viewModel = hiltViewModel<MainViewModel>()
    val context = LocalContext.current;

    Column {
        AreaSearchBar(modifier = Modifier)
        AreaContents(modifier = Modifier, refAreaIds)
        BasicButton(modifier = Modifier, onClick = {
            Log.d("ACP", refAreaIds.toString())
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = viewModel.trySocialSignUp(SocialSignupRequest(nickname, "profile.com", refAreaIds.toIntArray().toList(), idToken))
                    Log.d("ACP", "success: ${response}")
                }
                catch (e: HttpException){
                    val message = e.response()?.errorBody()?.toString()
                    Log.d("ACP", "error: $message")
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }, text = "회원가입!", networkBoolean = false)
    }


}