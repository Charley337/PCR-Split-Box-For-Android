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
            Text(
                text = "  请选择当前阶段  ",
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = Color.Gray,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .clickable { /*TODO*/ }
            )
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
            Text(
                text = "  选  择  ",
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = Color.Gray,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .clickable { /*TODO*/ }
            )
            Text(
                text = "  选  择  ",
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = Color.Gray,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .clickable { /*TODO*/ }
            )
            Text(
                text = "  选  择  ",
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = Color.Gray,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .clickable { /*TODO*/ }
            )
        }
    }
}

@Composable
fun ResultCard() {
    CardBorder {
        /*TODO*/
        Text(
            text = "Debug Log\n${Util.debugMainFunction()}",
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