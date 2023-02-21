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
import androidx.compose.ui.unit.dp

@Composable
fun PlanItem(mainViewModel: MainViewModel, mainActivity: MainActivity, i: Int) {
    Text(
        text = "方案${i + 1}    伤害：${mainViewModel.resultPlanList[i].damage}W    毛分：${mainViewModel.resultPlanList[i].score}W",
        modifier = Modifier.fillMaxWidth(0.9F)
    )
    Text(
        text = "需要嫖： ${mainViewModel.resultPlanList[i].borrow}",
        modifier = Modifier
            .fillMaxWidth(0.9F)
            .padding(bottom = 4.dp)
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxWidth(0.9F)
            .padding(bottom = 16.dp)
            .border(
                width = 2.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(4.dp)
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(4.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = mainViewModel.resultPlanList[i].h1.sn,
                    modifier = Modifier.clickable {
                        mainViewModel.onSnClicked(mainViewModel.resultPlanList[i].h1.sn)
                        mainActivity.toastShortShow("已添加到已使用列表")
                    }
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = mainViewModel.resultPlanList[i].h1.name1)
                    Text(text = mainViewModel.resultPlanList[i].h1.name2)
                    Text(text = mainViewModel.resultPlanList[i].h1.name3)
                    Text(text = mainViewModel.resultPlanList[i].h1.name4)
                    Text(text = mainViewModel.resultPlanList[i].h1.name5)
                    Text(
                        text = "${mainViewModel.resultPlanList[i].h1.damage}W",
                        modifier = Modifier.border(
                            width = 1.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(2.dp)
                        )
                    )
                }
            }
            for (j in mainViewModel.resultPlanList[i].h1.video.indices) {
                Text(
                    text = "视频${j + 1}：${mainViewModel.resultPlanList[i].h1.video[j].text}",
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = mainViewModel.resultPlanList[i].h1.video[j].url,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .clickable {
                            mainActivity.jumpToBrowserActivity(
                                mainViewModel.resultPlanList[i].h1.video[j].url
                            )
                        }
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(4.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = mainViewModel.resultPlanList[i].h2.sn,
                    modifier = Modifier.clickable {
                        mainViewModel.onSnClicked(mainViewModel.resultPlanList[i].h2.sn)
                        mainActivity.toastShortShow("已添加到已使用列表")
                    }
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = mainViewModel.resultPlanList[i].h2.name1)
                    Text(text = mainViewModel.resultPlanList[i].h2.name2)
                    Text(text = mainViewModel.resultPlanList[i].h2.name3)
                    Text(text = mainViewModel.resultPlanList[i].h2.name4)
                    Text(text = mainViewModel.resultPlanList[i].h2.name5)
                    Text(
                        text = "${mainViewModel.resultPlanList[i].h2.damage}W",
                        modifier = Modifier.border(
                            width = 1.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(2.dp)
                        )
                    )
                }
            }
            for (j in mainViewModel.resultPlanList[i].h2.video.indices) {
                Text(
                    text = "视频${j + 1}：${mainViewModel.resultPlanList[i].h2.video[j].text}",
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = mainViewModel.resultPlanList[i].h2.video[j].url,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .clickable {
                            mainActivity.jumpToBrowserActivity(
                                mainViewModel.resultPlanList[i].h1.video[j].url
                            )
                        }
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(4.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = mainViewModel.resultPlanList[i].h3.sn,
                    modifier = Modifier.clickable {
                        mainViewModel.onSnClicked(mainViewModel.resultPlanList[i].h3.sn)
                        mainActivity.toastShortShow("已添加到已使用列表")
                    }
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = mainViewModel.resultPlanList[i].h3.name1)
                    Text(text = mainViewModel.resultPlanList[i].h3.name2)
                    Text(text = mainViewModel.resultPlanList[i].h3.name3)
                    Text(text = mainViewModel.resultPlanList[i].h3.name4)
                    Text(text = mainViewModel.resultPlanList[i].h3.name5)
                    Text(
                        text = "${mainViewModel.resultPlanList[i].h3.damage}W",
                        modifier = Modifier.border(
                            width = 1.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(2.dp)
                        )
                    )
                }
            }
            for (j in mainViewModel.resultPlanList[i].h3.video.indices) {
                Text(
                    text = "视频${j + 1}：${mainViewModel.resultPlanList[i].h3.video[j].text}",
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = mainViewModel.resultPlanList[i].h3.video[j].url,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .clickable {
                            mainActivity.jumpToBrowserActivity(
                                mainViewModel.resultPlanList[i].h1.video[j].url
                            )
                        }
                )
            }
        }
    }
}