package com.wafflestudio.bunnybunny.pages

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.Navigation.findNavController
import com.wafflestudio.bunnybunny.R
import com.wafflestudio.bunnybunny.components.UI.dropShadow
import com.wafflestudio.bunnybunny.data.example.SocialLoginRequest
import com.wafflestudio.bunnybunny.lib.network.dto.SocialLoginResponse
import com.wafflestudio.bunnybunny.lib.network.getKakaoOAuthToken
import com.wafflestudio.bunnybunny.viewModel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@Composable
fun StartPage(
    modifier: Modifier = Modifier,
    onNavigateToSignUp : () -> Unit,
    onNavigateToSocialSignUp: (idToken: String) -> Unit,
    onNavigateToSignIn : () -> Unit,
    //onNavigateToKakaoSignIn : () -> Unit,
    //onNavigateToGoogleSignIn : () -> Unit,
    //onNavigateToMain : () -> Unit
) {
    val viewModel = hiltViewModel<MainViewModel>()
    var emailInput by rememberSaveable { mutableStateOf("") }
    var pwInput by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically,
//        ) {
        Image(
            painter = painterResource(id = R.drawable.bunnybunny_square),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.72f)
                .aspectRatio(1f)
                .clip(CircleShape)

        )
        //ID Input
        Row {
            BasicTextField(
                value = emailInput,
                onValueChange = { newText -> emailInput = newText },
                modifier = Modifier
                    .fillMaxWidth(0.72f)
                    .padding(5.dp)
                    .border(
                        width = 2.dp,
                        color = Color(1.0f, 0.647f, 0.0f, 1.0f),
                        shape = RoundedCornerShape(percent = 40)
                    ),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                            .background(
                                color = Color(0x0000000),
                                shape = RoundedCornerShape(percent = 40)
                            )
                            .padding(12.dp),
                    ) {
                        if (emailInput.isEmpty()) {
                            Text("Input E-mail", color = Color.Gray)
                        }
                        innerTextField()
                    }
                }
            )
        }


        //PW Input
        BasicTextField(
            value = pwInput,
            onValueChange = { newText -> pwInput = newText },
            modifier = Modifier
                .fillMaxWidth(0.72f)
                .padding(5.dp)
                .border(
                    width = 2.dp, color = Color(1.0f, 0.647f, 0.0f, 1.0f),
                    shape = RoundedCornerShape(percent = 40)
                ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    CoroutineScope(Dispatchers.IO).launch {
                        val loginRequest = viewModel.tryLogin(emailInput, pwInput)
                        withContext(Dispatchers.Main) {
                            // TODO onNavigateToMain
                        }
                    }
                }
            ),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color(0x0000000),
                            shape = RoundedCornerShape(percent = 40)
                        )
                        .padding(12.dp),
                ) {
                    if (pwInput.isEmpty()) {
                        Text("Input PW", color = Color.Gray)
                    }
                    innerTextField()
                }
            }
        )

        //ID & PW Sign in Button
        Button(
            modifier = Modifier
                .padding(4.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(1.0f, 0.647f, 0.0f, 1.0f)),
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    val loginResponse = viewModel.tryLogin(emailInput, pwInput)
                    Log.d("loginResponse", "${loginResponse}")
//                        val ifLogin = viewModel.api.testLogin("Bearer " + loginRequest.token)
                    withContext(Dispatchers.Main) {
                        if (loginResponse != null) {
                            onNavigateToSignIn()
                        }
                    }
                }
            }
        ) {
            Text("Sign In", softWrap = true)
        } //Sign up Button
        Button(
            modifier = Modifier.padding(12.dp),
            onClick = onNavigateToSignUp
        ) {
            Text("Sign up")
        }

        //Kakao Sign-in Button
        Image(painter = painterResource(id = R.drawable.kakao_login_large_narrow),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .padding(4.dp)
                .scale(1.2f, 1.2f)
                .align(alignment = Alignment.CenterHorizontally)
                .clickable {
                    CoroutineScope(Dispatchers.IO).launch {
                        val oAuthToken = getKakaoOAuthToken(context)
                        try {
                            if (oAuthToken != null) {
                                val socialLoginResponse =
                                    viewModel.trySocialLogin(SocialLoginRequest(oAuthToken.idToken!!))
                                Log.d("socialLoginResponse", "${socialLoginResponse}")
                            } else {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(context, "토큰이 만료되었습니다", Toast.LENGTH_SHORT)
                                        .show()
                                }

                            }
                        } catch (e: Exception) {
                            withContext(Dispatchers.Main) {
                                val idToken = oAuthToken!!.idToken
                                Toast.makeText(context, "회원가입부터 진행해주세요!", Toast.LENGTH_SHORT).show()
                                onNavigateToSocialSignUp(idToken!!)

                            }
                        }

                    }
                }
        )
    }
}




//        }
//        Column(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {



            //Google Sign-in Button
//            Button(
//                modifier = Modifier.padding(12.dp),
//                onClick = {print("a")},
//                colors = ButtonDefaults.buttonColors(
//                    Color.White,
//                    contentColor = Color.Black
//                )
//            ){
//                Text("G")
//            }
//        }
//    }

//}