package com.example.pcrsplitboxforandroid

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingPage(mainViewModel: MainViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "禁用角色", modifier = Modifier.padding(vertical = 16.dp))
        TextField(
            value = mainViewModel.tfBanListValue,
            modifier = Modifier.fillMaxWidth(0.8F).padding(vertical = 16.dp),
            onValueChange = { mainViewModel.tfBanListValue = it }
        )
        Text(text = "缺少角色（没有但可以嫖）", modifier = Modifier.padding(vertical = 16.dp))
        TextField(
            value = mainViewModel.tfLackListValue,
            modifier = Modifier.fillMaxWidth(0.8F).padding(vertical = 16.dp),
            onValueChange = { mainViewModel.tfLackListValue = it }
        )
    }
}