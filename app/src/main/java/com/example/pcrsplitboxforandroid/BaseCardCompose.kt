package com.example.pcrsplitboxforandroid

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pcrhelper.Util

@Composable
fun BaseCard(mainViewModel: MainViewModel) {
    CardBorder {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text(text = "PCR自动分刀工具", fontWeight = FontWeight.Black, fontSize = 20.sp)
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.younger_sister),
                contentDescription = "logo",
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .border(
                        width = 2.dp,
                        color = Color.Gray,
                        shape = RoundedCornerShape(20.dp)
                    )
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(text = mainViewModel.txtDataStateText, fontWeight = FontWeight.Bold)
                Button(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .padding(top = 8.dp),
                    onClick = { mainViewModel.onBtnGetDataClicked() }
                ) {
                    Text(text = "点击获取")
                }
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            if (mainViewModel.btnChooseStageState) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    for (i in 1..5) {
                        Text(
                            text = "  选择阶段:  ${Util.numberToEnChar[i]}  面  ",
                            modifier = Modifier
                                .border(
                                    width = 1.dp,
                                    color = Color.Gray,
                                    shape = RoundedCornerShape(2.dp)
                                )
                                .clickable { mainViewModel.onBtnChooseStageClicked2(i) }
                        )
                    }
                }
            } else {
                Text(
                    text = mainViewModel.btnChooseStageText,
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .clickable { mainViewModel.onBtnChooseStageClicked1() }
                )
            }
            Button(
                modifier = Modifier.clip(RoundedCornerShape(10.dp)),
                onClick = { mainViewModel.onBtnGetPlanListClicked() }
            ) {
                Text(text = "计算所有方案")
            }
        }
    }
}