package com.wafflestudio.bunnybunny.pages

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wafflestudio.bunnybunny.data.example.LoginRequest
import com.wafflestudio.bunnybunny.lib.network.api.BunnyApi
import com.wafflestudio.bunnybunny.viewModel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.ThreadPoolExecutor.DiscardPolicy
import javax.inject.Inject


@Composable
fun StartPage(
    modifier: Modifier = Modifier,
    onNavigateToSignUp : () -> Unit,
    //onNavigateToSignIn : () -> Unit,
    //onNavigateToKakaoSignIn : () -> Unit,
    //onNavigateToGoogleSignIn : () -> Unit,
    //onNavigateToMain : () -> Unit
){
    val viewModel = hiltViewModel<MainViewModel>()
    var emailInput by rememberSaveable { mutableStateOf("") }
    var pwInput by rememberSaveable { mutableStateOf("") }

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier.weight(2.5f)
            ) {
                //ID Input
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

                //PW Input
                BasicTextField(
                    value = pwInput,
                    onValueChange = { newText -> pwInput = newText},
                    modifier = Modifier.padding(5.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            CoroutineScope(Dispatchers.IO).launch{
                                val loginRequest = viewModel.tryLogin(emailInput, pwInput)
                                withContext(Dispatchers.Main){
                                    // TODO onNavigateToMain
                                }
                            }
                        }
                    ),
                    decorationBox = {
                            innerTextField ->
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
            }

            //ID & PW Sign in Button
            Button(
                modifier = Modifier
                    .padding(12.dp)
                    .weight(1f),
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch{
                        val loginRequest = viewModel.tryLogin(emailInput, pwInput)
                        //val ifLogin = viewModel.api.testLogin("Bearer " + loginRequest.token)
                        withContext(Dispatchers.Main){
                            // TODO onNavigateToMain
                        }
                    }
                }

            ){
                Text("Sign in")
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {

            //Sign up Button
            Button(
                modifier = Modifier.padding(12.dp),
                onClick = onNavigateToSignUp
            ){
                Text("Sign up")
            }

            //Kakao Sign-in Button
            Button(
                modifier = Modifier.padding(12.dp),
                onClick = {print("a")},
                colors = ButtonDefaults.buttonColors(
                    Color.Yellow,
                    contentColor = Color.Black
                )
            ){
                Text("K")
            }

            //Google Sign-in Button
            Button(
                modifier = Modifier.padding(12.dp),
                onClick = {print("a")},
                colors = ButtonDefaults.buttonColors(
                    Color.White,
                    contentColor = Color.Black
                )
            ){
                Text("G")
            }
        }
    }

}