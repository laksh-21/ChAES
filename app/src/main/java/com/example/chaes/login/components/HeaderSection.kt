package com.example.chaes.login.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun HeaderSection(
    mainText: String,
    subText: String,
){
    Text(
        text = mainText,
        style = MaterialTheme.typography.h4,
        modifier = Modifier.fillMaxWidth(.9f),
        textAlign = TextAlign.Start,
        fontWeight = FontWeight.SemiBold
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = subText,
        style = MaterialTheme.typography.subtitle2,
        modifier = Modifier.fillMaxWidth(.9f),
        textAlign = TextAlign.Start,
        color = MaterialTheme.colors.onBackground.copy(alpha = 0.75f)
    )
    Spacer(modifier = Modifier.height(16.dp))
}