package com.example.chaes.ui.detail.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.chaes.models.Message
import com.example.chaes.utilities.Constants
import timber.log.Timber

@Composable
fun MessagesList(
    modifier: Modifier = Modifier,
    messages: List<Message>
){
    LazyColumn(
        reverseLayout = true,
        modifier = modifier
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
        Timber.i("%s", (messages.size))
        items(messages){ message ->
            if(message.senderName == Constants.fromUserMessageName){
                MessageTo(message = message)
            } else{
                MessageFrom(message = message)
            }
        }
    }
}