package com.wafflestudio.bunnybunny.viewModel

import androidx.lifecycle.ViewModel
import com.wafflestudio.bunnybunny.data.example.ExampleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val repository: ExampleRepository
): ViewModel()