package com.wafflestudio.bunnybunny.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ParcelableMutableList(val myMutableList: MutableList<Int>) : Parcelable
