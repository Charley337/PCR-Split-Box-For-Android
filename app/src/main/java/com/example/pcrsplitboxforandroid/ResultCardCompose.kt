package com.example.pcrsplitboxforandroid

import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ResultCard(mainViewModel: MainViewModel) {
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
                onClick = { mainViewModel.onBtnGoClicked() }
            ) {
                Text(text = "Go!")
            }
            Text(
                text = mainViewModel.systemLog,
                modifier = Modifier
                    .fillMaxWidth(0.7F)
                    .border(
                        width = 1.dp,
                        color = Color.Gray,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 2.dp)
                    .horizontalScroll(rememberScrollState())
            )
        }
        for (i in mainViewModel.resultPlanList.indices) {
            PlanItem(mainViewModel, i)
        }
    }
}