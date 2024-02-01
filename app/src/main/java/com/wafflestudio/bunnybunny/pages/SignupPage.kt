package com.wafflestudio.bunnybunny.pages

import android.content.Context
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.annotation.UiThread
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.addAdapter
import com.wafflestudio.bunnybunny.R
import com.wafflestudio.bunnybunny.components.UI.bunnyColor
import com.wafflestudio.bunnybunny.components.compose.BasicButton
import com.wafflestudio.bunnybunny.components.compose.BunnyButton
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
import java.util.regex.Pattern

@Composable
fun SignupPage(
    modifier: Modifier = Modifier,
    onNavigateToAreaSearch : (emailInput: String, pwInput: String, nickname: String) -> Unit,
    context: Context
){
    val viewModel = hiltViewModel<MainViewModel>()
    var emailInput by rememberSaveable { mutableStateOf("") }
    var pwInput by rememberSaveable { mutableStateOf("") }
    var nickname by rememberSaveable { mutableStateOf("") }

    emailInput = "ba@naver.com"
    pwInput = "bani123!"
    nickname = "고려거란전쟁"

    var isDuplicateNick by rememberSaveable { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.bunnybunny_square),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.4f)
                .aspectRatio(1f)
                .clip(CircleShape)
        )
        Text("회원가입", modifier = Modifier.padding(8.dp), fontSize = 32.sp, fontWeight = FontWeight.ExtraBold, textAlign = TextAlign.Center)
        Spacer(Modifier.height(32.dp))
        LoginInputTextField(
            value = emailInput,
            onValueChange = { newText -> emailInput = newText },
            placeholder = "이메일을 입력해주세요",
        )
        if (!isRegularEmail(emailInput))
            Text(text = "이메일 형식이 올바르지 않습니다.", color = Color.Red, fontSize = 10.sp, textAlign = TextAlign.Start, modifier = Modifier.fillMaxWidth(0.85f))
        LoginInputTextField(value = pwInput,
            onValueChange ={ newText -> pwInput = newText },
            placeholder = "비밀번호를 입력해주세요",
        )
        if (!isRegularPw(pwInput))
            Text(text = "비밀번호는 8~16자 영문 소문자, 숫자, 특수문자가 하나씩은 포함되어야 합니다.", color = Color.Red, fontSize = 10.sp)
        Row(
            modifier.fillMaxWidth(0.9f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LoginInputTextField(
                value = nickname,
                onValueChange = {
                    newText -> nickname = newText
                    isDuplicateNick = 0
                },
                placeholder = "닉네임을 입력해주세요",
                fraction = 0.7f
            )
            BunnyButton(
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        isDuplicateNick = try {
                            viewModel.tryCheckDuplicateNickname(nickName = nickname)
                            1
                        } catch (e: Exception) {
                            -1
                        }
                    }
                },
                text = "중복 체크",
                fontSize = 12.sp,
            )
        }
        if (!isRegularNickname(nickname))
            Text(text = "닉네임은 특수문자를 제외한 2~10자리여야 합니다.", color = Color.Red, fontSize = 10.sp, textAlign = TextAlign.Start, modifier = Modifier.fillMaxWidth(0.85f))
        if (isDuplicateNick == -1) {
            Text(text = "중복된 닉네임입니다.", color = Color.Red, fontSize = 10.sp, textAlign = TextAlign.Start, modifier = Modifier.fillMaxWidth(0.85f))
        } else if (isDuplicateNick == 1) {
            Text(text = "사용할 수 있는 닉네임입니다.", color = Color.Black, fontSize = 10.sp, textAlign = TextAlign.Start, modifier = Modifier.fillMaxWidth(0.85f))
        }
        Spacer(Modifier.height(24.dp))
        BunnyButton(
            modifier = Modifier.fillMaxWidth(0.9f),
            onClick = { onNavigateToAreaSearch(emailInput, pwInput, nickname) },
            text = "다음",
            networkBoolean = isRegularPw(pwInput) && isRegularEmail(emailInput) && isRegularNickname(nickname) && isDuplicateNick == 1
        )
    }
}

private fun isRegularEmail(email: String): Boolean {
    val reg = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$"
    val pattern = Pattern.compile(reg)
    return pattern.matcher(email).matches()
}

private fun isRegularPw(password: String): Boolean {
    val reg = "^(?=.*[0-9])(?=.*[a-z])(?=.*[!@#$%^&+=]).{8,}$"
    val pattern = Pattern.compile(reg)
    return pattern.matcher(password).matches()
}

private fun isRegularNickname(nickname: String): Boolean {
    val reg = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$"
    val pattern = Pattern.compile(reg)
    return pattern.matcher(nickname).matches()
}