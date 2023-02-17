package com.example.pcrsplitboxforandroid

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pcrhelper.Util

@Composable
fun SelectCard(mainViewModel: MainViewModel) {
    CardBorder {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text(
                text = "选择Boss",
                fontWeight = FontWeight.Bold
            )
            for (i in 0 until 3) {
                if (mainViewModel.btnListChooseBossState[i]) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        for (j in 1..5) {
                            Text(
                                text = "  ${Util.numberToZhChar[j]}  王  ",
                                modifier = Modifier
                                    .border(
                                        width = 1.dp,
                                        color = Color.Gray,
                                        shape = RoundedCornerShape(2.dp)
                                    )
                                    .clickable { mainViewModel.onBtnListChooseBossClicked2(i, j) }
                            )
                        }
                    }
                } else {
                    Text(
                        text = mainViewModel.btnListChooseBossText[i],
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = Color.Gray,
                                shape = RoundedCornerShape(4.dp)
                            )
                            .clickable { mainViewModel.onBtnListChooseBossClicked1(i) }
                    )
                }
            }
        }
    }
}