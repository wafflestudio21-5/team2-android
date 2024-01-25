package com.wafflestudio.bunnybunny.model

import android.net.Uri

data class ToggleImageItem(
    val uri:Uri,
    val isSelected: Boolean=false,
    val selectedOrder: Int?=null,
)
