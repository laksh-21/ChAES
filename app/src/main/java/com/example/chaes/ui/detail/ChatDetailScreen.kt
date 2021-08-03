package com.example.chaes.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chaes.Screens
import com.example.chaes.ui.detail.components.MessageFrom
import com.example.chaes.ui.detail.components.MessageTextField
import com.example.chaes.ui.detail.components.MessageTo

@Composable
fun ChatDetailScreen(
    navController: NavController
){
    Scaffold(
        topBar = {
            TitleBar(navController = navController)
        }
    ) { paddingValues ->

    }
}

@Preview
@Composable
fun ChatColumn(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 8.dp,
                start = 8.dp,
                end = 8.dp,
                bottom = 0.dp
            ),
        verticalArrangement = Arrangement.Bottom
    ){
        ChatList()
        MessageTextField()
    }
}

@Composable
fun ChatList(){
    LazyColumn(
        modifier = Modifier,
        reverseLayout = true,
    ){
        item {
            MessageFrom()
        }
        item {
            MessageTo()
        }
    }
}

@Composable
fun TitleBar(
    navController: NavController,
){
    TopAppBar(
        title = {
            Text(
                text = "Title here",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.subtitle1
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack(
                    Screens.ChatDetail.route,
                    inclusive = true
                )
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Navigate Back"
                )
            }
        },
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.onBackground,
        elevation = 0.dp,
    )
}