package com.wafflestudio.bunnybunny.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.wafflestudio.bunnybunny.components.compose.AreaContents
import com.wafflestudio.bunnybunny.components.compose.AreaSearchBar

@Composable
fun AreaChoosePage() {
    Column {
        AreaSearchBar(modifier = Modifier)
        AreaContents(modifier = Modifier)
    }
}