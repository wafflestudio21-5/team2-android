package com.wafflestudio.bunnybunny.data.example

interface PrefRepository {
    fun getPref(key: String): String?
    fun setPref(key: String,data: String)
    fun clearPref(key:String)
}