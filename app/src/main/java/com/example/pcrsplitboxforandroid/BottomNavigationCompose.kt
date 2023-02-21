package com.example.pcrsplitboxforandroid

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pcrsplitboxforandroid.ui.theme.Purple200
import com.example.pcrsplitboxforandroid.ui.theme.Purple350

@Composable
fun BottomNavigationCompose(mainViewModel: MainViewModel) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.5F)
                .background(color = if (mainViewModel.navigationIndex == 0) Purple350 else Purple200)
                .clickable { mainViewModel.onBottomNavigationClicked(0) }
        ) {
            Text(
                text = "首页",
                letterSpacing = 6.sp
            )
        }
        Box(modifier = Modifier.fillMaxHeight().width(2.dp).background(MaterialTheme.colors.background))
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(color = if (mainViewModel.navigationIndex == 1) Purple350 else Purple200)
                .clickable { mainViewModel.onBottomNavigationClicked(1) }
        ) {
            Text(
                text = "设置",
                letterSpacing = 6.sp
            )
        }
    }
}