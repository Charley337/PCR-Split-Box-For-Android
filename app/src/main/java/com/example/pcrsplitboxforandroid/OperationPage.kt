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
fun BaseCard() {
    var btnChooseStageState by remember { mutableStateOf(false) }
    var btnChooseStageText: String = "  请选择当前阶段  "
    val numberToChar: String = " ABCDEFG"

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
                    onClick = { /*TODO*/ }
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
            if (btnChooseStageState) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    for (i in 1..5) {
                        Text(
                            text = "  选择阶段:  ${numberToChar[i]}  面  ",
                            modifier = Modifier
                                .border(
                                    width = 1.dp,
                                    color = Color.Gray,
                                    shape = RoundedCornerShape(2.dp)
                                )
                                .clickable {
                                    UiState.chooseStageState = i
                                    btnChooseStageState = !btnChooseStageState
                                    btnChooseStageText = "  当前阶段:  ${numberToChar[i]}  面  "
                                }
                        )
                    }
                }
            } else {
                Text(
                    text = btnChooseStageText,
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .clickable {
                            UiState.chooseStageState = 0
                            btnChooseStageState = !btnChooseStageState
                        }
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
fun SelectCard() {
    var btnListChooseBossState by remember { mutableStateOf(listOf(false, false, false)) }
    val numberToCharacter: Array<String> = arrayOf(
        "零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十"
    )
    val btnListChooseBossText: MutableList<String> = mutableListOf(
        "  选  择  ", "  选  择  ", "  选  择  "
    )

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
                if (btnListChooseBossState[i]) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        for (j in 1..5) {
                            Text(
                                text = "  ${numberToCharacter[j]}  王  ",
                                modifier = Modifier
                                    .border(
                                        width = 1.dp,
                                        color = Color.Gray,
                                        shape = RoundedCornerShape(2.dp)
                                    )
                                    .clickable {
                                        UiState.chooseBossState[i] = j
                                        btnListChooseBossText[i] = "  ${numberToCharacter[j]}  王  "
                                        val tempMutableList: MutableList<Boolean> = mutableListOf()
                                        for (k in 0 until 3) {
                                            if (i == k) {
                                                tempMutableList.add(!btnListChooseBossState[k])
                                            } else {
                                                tempMutableList.add(btnListChooseBossState[k])
                                            }
                                        }
                                        btnListChooseBossState = tempMutableList
                                    }
                            )
                        }
                    }
                } else {
                    Text(
                        text = btnListChooseBossText[i],
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = Color.Gray,
                                shape = RoundedCornerShape(4.dp)
                            )
                            .clickable {
                                UiState.chooseBossState[i] = 0
                                val tempMutableList: MutableList<Boolean> = mutableListOf()
                                for (j in 0 until 3) {
                                    if (i == j) {
                                        tempMutableList.add(!btnListChooseBossState[j])
                                    } else {
                                        tempMutableList.add(btnListChooseBossState[j])
                                    }
                                }
                                btnListChooseBossState = tempMutableList
                            }
                    )
                }
            }
        }
    }
}

@Composable
fun ResultCard() {
    var debugContent by remember { mutableStateOf("") }

    CardBorder {
        /*TODO*/
        Button(
            onClick = {
//                debugContent = Util.debugMainFunction()
                debugContent = "UiState: {chooseStageState: ${UiState.chooseStageState}, chooseBossState: ${UiState.chooseBossState}}"
            }
        ) {
            Text(text = "Go!")
        }
        Text(
            text = "Debug Log\n$debugContent",
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

@Composable
fun OperationPage() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        BaseCard()
        SelectCard()
        ResultCard()
    }
}

@Preview
@Composable
fun OperationPagePreview() {
    OperationPage()
}