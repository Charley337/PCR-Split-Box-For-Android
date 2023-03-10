package com.example.pcrsplitboxforandroid

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OperationPage(mainViewModel: MainViewModel, mainActivity: MainActivity) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        BaseCard(mainViewModel, mainActivity)
        SelectCard(mainViewModel)
        ResultCard(mainViewModel, mainActivity)
    }
}