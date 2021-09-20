package com.example.chaes.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chaes.models.Message
import com.example.chaes.ui.detail.components.MessageTextField
import com.example.chaes.ui.detail.components.MessagesList
import com.example.chaes.ui.detail.viewModel.ChatDetailViewModel
import timber.log.Timber

@Composable
fun ChatDetailScreen(
    navController: NavController,
    viewModel: ChatDetailViewModel,
    toUid: String?,
    name: String?,
){
    Timber.d(toUid)
    Scaffold(
        topBar = {
            TitleBar(
                navController = navController,
                name = name
            )
        },
    ) { paddingValues ->
        val messages = viewModel.messages.value
        Timber.i("We are recomposing: %d", messages.size)
        val messageText: String = viewModel.messageText.value
        ChatColumn(
            paddingValues = paddingValues,
            onClickSendButton = { viewModel.addMessage() },
            messages = messages,
            messageText = messageText,
            onMessageTextChanged = { viewModel.onMessageTextChanged(it) },
        )
    }

    DisposableEffect(viewModel){
        viewModel.handleInfo(uid = toUid, name = name)
        onDispose {
            viewModel.detachListener()
        }
    }
}

@Composable
fun ChatColumn(
    paddingValues: PaddingValues = PaddingValues(all = 0.dp),
    onClickSendButton: () -> Unit = {},
    messages: List<Message>,
    messageText: String,
    onMessageTextChanged: (String) -> Unit,
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = paddingValues)
            .padding(start = 8.dp, end = 8.dp, top = 8.dp)
            .background(
                color = MaterialTheme.colors.primary,
                shape = RoundedCornerShape(topStartPercent = 10, topEndPercent = 10)
            ),
        verticalArrangement = Arrangement.Bottom
    ) {
        Row(modifier = Modifier.weight(1f, true)){
            MessagesList(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 8.dp)
                ,
                messages = messages,
            )
        }
        Row{
            MessageTextField(
                messageText = messageText,
                onMessageTextChanged = onMessageTextChanged,
                onClickSendButton = { onClickSendButton() }
            )
        }
    }
}

@Composable
fun TitleBar(
    navController: NavController,
    name: String?
){
    TopAppBar(
        title = {
            Text(
                text = name?:"Title Here",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.subtitle1
            )
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
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