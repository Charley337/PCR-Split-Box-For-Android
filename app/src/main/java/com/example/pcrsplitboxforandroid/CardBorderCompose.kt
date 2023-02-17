package com.example.pcrsplitboxforandroid

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

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