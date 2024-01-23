package com.wafflestudio.bunnybunny.components.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun LoginPasswordTextField (
    pwInput: String,
    onPwChange: (String) -> Unit,
    onLoginClick: () -> Unit
) {
    BasicTextField(
        pwInput, { newText -> onPwChange(newText) }, Modifier
            .fillMaxWidth(0.9f)
            .padding(5.dp)
            .border(
                width = 1.dp,
                color = Color(1.0f, 0.647f, 0.0f, 1.0f),
                shape = RoundedCornerShape(percent = 20)
            ),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onLoginClick()
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
                    Text("비밀번호를 입력해주세요", color = Color.Gray)
                }
                innerTextField()
            }
        }
    )
}