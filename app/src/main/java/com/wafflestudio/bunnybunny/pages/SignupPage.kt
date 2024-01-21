package com.wafflestudio.bunnybunny.pages

import android.content.Context
import android.content.pm.PackageManager
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
import androidx.core.content.ContextCompat
import androidx.core.content.PackageManagerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.squareup.moshi.Moshi
import com.wafflestudio.bunnybunny.data.example.SignupRequest
import com.wafflestudio.bunnybunny.lib.network.data.HttpResult
import com.wafflestudio.bunnybunny.viewModel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException
import java.util.logging.Handler
import kotlin.math.sign

@Composable
fun SignupPage(
    modifier: Modifier = Modifier,
    onNavigateToStart : () -> Unit,
    context: Context
){
    val handler = android.os.Handler(Looper.getMainLooper())
    val viewModel = hiltViewModel<MainViewModel>()
    var emailInput by rememberSaveable { mutableStateOf("") }
    var pwInput by rememberSaveable { mutableStateOf("") }
    var nickname by rememberSaveable { mutableStateOf("") }
    Column {
        BasicTextField(
            value = emailInput,
            onValueChange = { newText -> emailInput = newText},
            modifier = Modifier.padding(5.dp),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color(0xFFFF8000),
                            shape = RoundedCornerShape(percent = 40)
                        )
                        .padding(12.dp),
                ){
                    if(emailInput.isEmpty()){
                        Text("Input E-mail", color = Color.Gray)
                    }
                    innerTextField()
                }
            }
        )
        BasicTextField(
            value = pwInput,
            onValueChange = { newText -> pwInput = newText},
            modifier = Modifier.padding(5.dp),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color(0xFFFF8000),
                            shape = RoundedCornerShape(percent = 40)
                        )
                        .padding(12.dp),
                ){
                    if(pwInput.isEmpty()){
                        Text("Input PW", color = Color.Gray)
                    }
                    innerTextField()
                }
            }
        )
        BasicTextField(
            value = nickname,
            onValueChange = { newText -> nickname = newText},
            modifier = Modifier.padding(5.dp),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color(0xFFFF8000),
                            shape = RoundedCornerShape(percent = 40)
                        )
                        .padding(12.dp),
                ){
                    if(nickname.isEmpty()){
                        Text("Input Nickname", color = Color.Gray)
                    }
                    innerTextField()
                }
            }
        )
        Button(
            modifier = Modifier.padding(5.dp),
            onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val signupInfo = viewModel.trySignup(
                                SignupRequest(
                                    emailInput, pwInput, nickname,
                                    "https://some.domain.name/path", listOf(0)
                                )
                            )
                            withContext(Dispatchers.Main){
                                onNavigateToStart()
                            }

                        }
                        catch (e: HttpException){
                            val jObjError = JSONObject(e.response()?.errorBody()!!.string()).getJSONObject("error")
                            val message = jObjError.getString("message")
                            handler.postDelayed({Toast.makeText(context, message, Toast.LENGTH_SHORT).show()}, 0)
                        }
                    }
            }
        ){
            Text("회원가입")
        }
        /*Button(
            modifier = Modifier.padding(5.dp),
            onClick = {
                when{
                    ContextCompat.checkSelfPermission(
                        context,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_GRANTED -> {

                    }



                    else ->
                }
            }
        ){

        }*/
    }
}