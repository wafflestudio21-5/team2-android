package com.wafflestudio.bunnybunny.pages

import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.wafflestudio.bunnybunny.components.compose.AreaContents
import com.wafflestudio.bunnybunny.components.compose.AreaSearchBar
import com.wafflestudio.bunnybunny.components.compose.BasicButton
import com.wafflestudio.bunnybunny.components.compose.BunnyButton
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

fun SocialAreaChoosePage(nickname: String, idToken: String, navController: NavController, onNavigateToSignIn: () -> Unit) {
    val refAreaIds = remember { mutableStateListOf<RefArea>() }
    val viewModel = hiltViewModel<MainViewModel>()
    val areaDetails by viewModel.areaDetails.collectAsState()
    val context = LocalContext.current;

    Column(modifier = Modifier.padding(vertical = 10.dp, horizontal = 10.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        AreaSearchBar(modifier = Modifier, areaDetails)
        AreaContents(modifier = Modifier, refAreaIds, areaDetails, navController, false)
        BunnyButton(modifier = Modifier, onClick = {
            Log.d("ACP", refAreaIds.toString())
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = viewModel.trySocialSignUp(SocialSignupRequest(nickname, "https://files.slack.com/files-pri/T06UKPBS8-F06FHL84UTH/default_profile_80-c649f052a34ebc4eee35048815d8e4f73061bf74552558bb70e07133f25524f9.png", refAreaIds.map { it.id }, idToken))
                    Log.d("ACP", "success: ${response}")
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "회원가입 성공!", Toast.LENGTH_SHORT).show()
                        onNavigateToSignIn()
                    }
                }
                catch (e: HttpException){
                    val message = e.response()?.errorBody()?.toString()
                    Log.d("ACP", "error: $message")
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }, text = "다음", networkBoolean = refAreaIds.isNotEmpty())
    }


}