package com.example.chaes.ui.detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun MessageTo(){
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.End
    ){
          Surface(
              color = Color.Cyan,
              modifier = Modifier.fillMaxWidth(0.75f)
          ){
              Text(text = "Message")
          }
    }
}