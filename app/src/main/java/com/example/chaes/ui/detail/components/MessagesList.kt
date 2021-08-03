package com.example.chaes.ui.detail.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun MessagesList(){
    LazyColumn(
        reverseLayout = true,
    ){
        item{
            MessageTo()
        }
        item {
            MessageFrom()
        }
        item{
            MessageTo()
        }
        item {
            MessageFrom()
        }
        item{
            MessageTo()
        }
        item {
            MessageFrom()
        }
    }
}