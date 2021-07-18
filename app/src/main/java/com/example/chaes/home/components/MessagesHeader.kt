package com.example.chaes.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MessagesHeader(){
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        shape = RoundedCornerShape(
            topStartPercent = 50,
            topEndPercent = 50,
            bottomStartPercent = 0,
            bottomEndPercent = 0,
        ),
        color = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 32.dp,
                    bottom = 8.dp,
                    start = 28.dp,
                    end = 28.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
        ){
            Text(
                text = "My messages",
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.subtitle1
            )
            Box(
                modifier = Modifier
                    .background(
                    color = MaterialTheme.colors.onPrimary,
                    shape = CircleShape)
                    .layout{ measurable, constraints ->
                        val placeable = measurable.measure(constraints = constraints)
                        //get the current max dimension to assign width=height
                        val currentHeight = placeable.height
                        var heightCircle = currentHeight
                        if (placeable.width > heightCircle)
                            heightCircle = placeable.width

                        //assign the dimension and the center position
                        layout(heightCircle, heightCircle) {
                            // Where the composable gets placed
                            placeable.placeRelative(0, (heightCircle-currentHeight)/2)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "0",
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.subtitle1,
                    color = MaterialTheme.colors.primary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(4.dp)
                        .defaultMinSize(24.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun MessagesPreviewDemo(){
    MessagesHeader()
}