package com.example.chaes.ui.detail

import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chaes.models.Message
import com.example.chaes.ui.detail.components.MessageTextField
import com.example.chaes.ui.detail.components.MessagesList
import com.example.chaes.ui.detail.viewModel.ChatDetailViewModel
import com.example.chaes.utilities.Constants.dummyUID
import com.example.chaes.utilities.NavigationRoutes.chatDetailScreenRoute
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun ChatDetailScreen(
    navController: NavController,
    viewModel: ChatDetailViewModel,
    toUid: String?
){
    Timber.d(toUid)
    Scaffold(
        topBar = {
            TitleBar(navController = navController)
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
        viewModel.attachListener(toUid)
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
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        Row(modifier = Modifier.weight(1f, true)){
            MessagesList(
                modifier = Modifier.fillMaxSize(),
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