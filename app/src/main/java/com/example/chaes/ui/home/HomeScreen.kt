package com.example.chaes.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chaes.ui.home.components.ConversationCard
import com.example.chaes.ui.home.components.MessagesHeader
import com.example.chaes.ui.home.components.SearchUserTextField
import com.example.chaes.ui.home.viewModel.HomeScreenViewModel
import com.example.chaes.models.Conversation
import com.example.chaes.repository.callbacks.UserExistsCallback
import com.example.chaes.ui.home.components.TitleBar
import com.example.chaes.utilities.Constants.dummyUID
import com.example.chaes.utilities.NavigationRoutes.chatDetailScreenRoute
import com.example.chaes.utilities.NavigationRoutes.splashScreenRoute
import timber.log.Timber

@ExperimentalMaterialApi
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel,
){
    val menuOpened = viewModel.menuOpened.value
    Scaffold(
        topBar = {
            TitleBar(
                menuOpened = menuOpened,
                onClickMoreDots = { viewModel.onClickMoreDots() },
                onClickSignOut = {
                    viewModel.onClickSignOut()
                    navController.navigate(splashScreenRoute){
                        popUpTo(0)
                    }
                }
            )
        }
    ) {
        val conversations = viewModel.conversations.value
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            val searchText: String by viewModel.searchUserText.observeAsState("")
            SearchSection(
                searchText = searchText,
                onSearchUserTextChanged = { viewModel.onSearchUserTextChanged(it) },
                onSearchUserClicked = {
                    viewModel.onSearchUserClicked(
                        callback = object : UserExistsCallback {
                            override fun userExists(
                                uid: String,
                                name: String
                            ) {
                                Timber.d("User does exist $uid")
                                navController.navigate("$chatDetailScreenRoute/$uid/$name")
                            }

                            override fun userDoesNotExist() {
                                Timber.d("User does not exist")
                            }

                            override fun userCheckFailed() {
                                Timber.d("Something went wrong. Try again")
                            }
                        }
                    )
                }
            )
            MessagesHeader(unreadCount = countUnread(conversations = conversations))
            ConversationsList(
                conversations = conversations,
                navController = navController,
                setConversationOpened = { viewModel.setConversationOpened(uid = it) }
            )
        }
    }

//    DisposableEffect(viewModel){
//        viewModel.attachListener()
//        onDispose{
//            viewModel.detachListener()
//        }
//    }
}

fun countUnread(conversations: List<Conversation>): Int {
    var count = 0
    for (conversation in conversations) {
        if(!conversation.isOpened) count++
    }
    return count
}

@Composable
fun SearchSection(
    searchText: String,
    onSearchUserTextChanged: (String) -> Unit,
    onSearchUserClicked: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 20.dp)
            .background(color = Color.Transparent),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Add a new conversation",
            style = MaterialTheme.typography.h4,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(16.dp))
        SearchUserTextField(
            searchUserText = searchText,
            onSearchUserTextChanged = { onSearchUserTextChanged(it) },
            onSearchUserClicked = { onSearchUserClicked() }
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun ConversationsList(
    conversations: List<Conversation>,
    navController: NavController,
    setConversationOpened: (String) -> Unit
){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colors.primary,
                shape = RectangleShape
            )
    ) {
        items(conversations){ conversation ->
            ConversationCard(
                conversation = conversation,
                onConversationClick = {
                    if(!conversation.isOpened){
                        setConversationOpened(conversation.uid)
                    }
                    navController.navigate(
                        "$chatDetailScreenRoute/${conversation.uid}/${conversation.name}"
                    )
                }
            )
        }
    }
}