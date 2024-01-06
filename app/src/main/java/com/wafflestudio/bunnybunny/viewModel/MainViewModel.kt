package com.wafflestudio.bunnybunny.viewModel

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.ViewModel
import com.wafflestudio.bunnybunny.data.example.LoginRequest
import com.wafflestudio.bunnybunny.data.example.LoginResponse
import com.wafflestudio.bunnybunny.lib.network.api.BunnyApi
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val api: BunnyApi
): ViewModel() {

    companion object {}
    suspend fun tryLogin(email: String, password: String): LoginResponse{
            return api.loginRequest(LoginRequest(email, password))
    }

}