package com.wafflestudio.bunnybunny.utils
fun mutableListToString(mutableList: MutableList<Int>): String {
        return mutableList.joinToString(",")
}

    // 문자열을 MutableList로 변환하는 함수
fun stringToMutableList(str: String): MutableList<Int> {
        return str.split(",").map { it.toInt() }.toMutableList()
}
