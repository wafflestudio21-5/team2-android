package com.wafflestudio.bunnybunny.pages

import android.content.Context
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.annotation.UiThread
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.addAdapter
import com.wafflestudio.bunnybunny.components.compose.BasicButton
import com.wafflestudio.bunnybunny.components.compose.LoginInputTextField
import com.wafflestudio.bunnybunny.components.compose.LoginPasswordTextField
import com.wafflestudio.bunnybunny.data.example.ErrorResponse
import com.wafflestudio.bunnybunny.data.example.SignupRequest
import com.wafflestudio.bunnybunny.viewModel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.util.logging.Handler

@Composable
fun SocialSignupPage(
    modifier: Modifier = Modifier,
    onNavigateToAreaSearch : () -> Unit,
    context: Context
){
    val handler = android.os.Handler(Looper.getMainLooper())
    val viewModel = hiltViewModel<MainViewModel>()
    var emailInput by rememberSaveable { mutableStateOf("") }
    var pwInput by rememberSaveable { mutableStateOf("") }
    var nickname by rememberSaveable { mutableStateOf("") }
    //var pwInput by rememberSaveable { mutableStateOf("") }
    Column {
        LoginInputTextField(
            value = nickname,
            onValueChange = { newText -> nickname = newText },
            placeholder = "닉네임을 입력해주세요"
        )
        BasicButton(modifier = Modifier, onClick = { onNavigateToAreaSearch() }, text = "지역 선택하기", networkBoolean = false)
    }
}