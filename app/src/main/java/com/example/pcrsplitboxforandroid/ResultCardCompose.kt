package com.example.pcrsplitboxforandroid

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ResultCard(mainViewModel: MainViewModel, mainActivity: MainActivity) {
    CardBorder {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Button(
                enabled = mainViewModel.btnGoEnabled,
                onClick = { mainViewModel.onBtnGoClicked(mainActivity) }
            ) {
                Text(text = "Go!")
            }
            TextField(
                value = mainViewModel.tfValue,
                onValueChange = { mainViewModel.tfValue = it },
                modifier = Modifier
                    .fillMaxWidth(0.7F)
                    .horizontalScroll(rememberScrollState())
            )
        }
        for (i in mainViewModel.resultPlanList.indices) {
            PlanItem(mainViewModel, mainActivity, i)
        }
    }
}