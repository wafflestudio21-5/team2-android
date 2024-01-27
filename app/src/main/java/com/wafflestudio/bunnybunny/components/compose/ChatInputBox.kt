package com.wafflestudio.bunnybunny.components.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.hilt.navigation.compose.hiltViewModel
import com.wafflestudio.bunnybunny.viewModel.ChatViewModel
import com.wafflestudio.bunnybunny.viewModel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.WebSocket
import retrofit2.HttpException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatInputBox(
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel
) {
    var message by remember { mutableStateOf("") }
    var isKeyboardVisible by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Row {
        Column(
            modifier = modifier
                .fillMaxWidth(0.85f)
        ) {
            TextField(
                value = message,
                onValueChange = { message = it },
                modifier = Modifier
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(
                    onSend = {
                        if (message.isNotBlank()) {
                            CoroutineScope(Dispatchers.IO).launch {
                                viewModel.sendMessage(message)
                            }
                            message = ""
                        }
                    }
                ),
                placeholder = { Text("Type a message...") },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )


        }

        Column(
            modifier = modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon (
                imageVector = Icons.Default.Send,
                contentDescription = "Search Icon",
                modifier = Modifier
                    .fillMaxSize(0.8f)
                    .clickable {
                        if (message.isNotBlank()) {
                            CoroutineScope(Dispatchers.IO).launch {
                                viewModel.sendMessage(message)
                                message = ""
                            }
                        }
                    },
                tint = Color(1.0f, 0.647f, 0.0f, 1.0f),
            )

        }

    }

}

