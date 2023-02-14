package com.example.pcrsplitboxforandroid

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pcrhelper.Util

@Composable
fun CardBorder(content: @Composable ColumnScope.() -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 4.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(25.dp)
            )
            .padding(top = 16.dp)
    ) {
        content()
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(16.dp)
    ) {

    }
}

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
                Text(text = "尚未获取数据", fontWeight = FontWeight.Bold)
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
                onClick = { /*TODO*/ }
            ) {
                Text(text = "计算所有方案")
            }
        }
    }
}

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

@Composable
fun ResultCard(mainViewModel: MainViewModel) {
    CardBorder {
        /*TODO*/
        Button(
            onClick = { mainViewModel.onBtnGoClicked() }
        ) {
            Text(text = "Go!")
        }
        Text(
            text = "Debug Log\n${mainViewModel.debugContent}",
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

@Composable
fun OperationPage(mainViewModel: MainViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        BaseCard(mainViewModel)
        SelectCard(mainViewModel)
        ResultCard(mainViewModel)
    }
}

//@Preview
//@Composable
//fun OperationPagePreview() {
//    OperationPage()
//}