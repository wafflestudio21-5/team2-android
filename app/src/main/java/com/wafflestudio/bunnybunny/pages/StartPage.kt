package com.wafflestudio.bunnybunny.pages

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
@Preview(showBackground = true)
@Composable
fun StartPage(
    modifier: Modifier = Modifier,
    //onNavigateToSignUp : () -> Unit,
    //onNavigateToSignIn : () -> Unit,
    //onNavigateToKakaoSignIn : () -> Unit,
    //onNavigateToGoogleSignIn : () -> Unit,
){
    var idInput by rememberSaveable { mutableStateOf("") }
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
                    value = idInput,
                    onValueChange = { newText -> idInput = newText},
                    modifier = Modifier.padding(12.dp),
                    decorationBox = {
                            innerTextField ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = Color(0xFFFF8000),
                                    shape = RoundedCornerShape(size = 16.dp)
                                )
                                .padding(16.dp),
                        ){
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "",
                                tint = Color.DarkGray,
                            )
                        }
                    }
                )
                //PW Input
                BasicTextField(
                    value = pwInput,
                    onValueChange = { newText -> pwInput = newText},
                    modifier = Modifier.padding(12.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            // TODO
                        }
                    ),
                    decorationBox = {
                        // TODO
                    }
                )
            }
            //ID & PW Sign in Button
            Button(
                modifier = Modifier.padding(12.dp).weight(1f),
                onClick = {print("a")}
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
                onClick = {print("a")}
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