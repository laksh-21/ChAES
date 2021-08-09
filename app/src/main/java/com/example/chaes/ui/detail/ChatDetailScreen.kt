package com.example.chaes.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.chaes.ui.detail.components.MessageTextField
import com.example.chaes.ui.detail.components.MessagesList
import com.example.chaes.ui.detail.viewModel.ChatDetailViewModel
import com.example.chaes.utilities.NavigationRoutes.chatDetailScreenRoute

@Composable
fun ChatDetailScreen(
    navController: NavController,
    viewModel: ChatDetailViewModel,
){
    Scaffold(
        topBar = {
            TitleBar(navController = navController)
        },
    ) { paddingValues ->
        ChatColumn(
            paddingValues = paddingValues,
            onClickSendButton = { viewModel.addMessage() }
        )
    }

    DisposableEffect(viewModel){
        viewModel.onCompose()
        onDispose {
            viewModel.deCompose()
        }
    }
}

@Preview
@Composable
fun ChatColumn(
    paddingValues: PaddingValues = PaddingValues(all = 0.dp),
    onClickSendButton: () -> Unit = {}
){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        Row(modifier = Modifier.weight(1f, true)){
            MessagesList(modifier = Modifier.fillMaxSize())
        }
        Row{
            MessageTextField(
                onClickSendButton = { onClickSendButton() }
            )
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
                    chatDetailScreenRoute,
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