package com.example.chaes.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.chaes.home.components.ConversationCard
import com.example.chaes.home.components.MessagesHeader
import com.example.chaes.home.components.SearchUserTextField
import com.example.chaes.home.viewModel.HomeScreenViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel(),
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
    ) {
        val searchText: String by viewModel.searchUserText.observeAsState("")
        SearchSection(
            searchText = searchText,
            onSearchUserTextChanged = { viewModel.onSearchUserTextChanged(it) }
        )
        MessagesHeader()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colors.primary,
                    shape = RectangleShape
                )
        ) {
            item {
                ConversationCard()
            }
        }
    }
}

@Composable
fun SearchSection(
    searchText: String,
    onSearchUserTextChanged: (String) -> Unit,
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
            onSearchUserTextChanged = { onSearchUserTextChanged(it) }
        )
    }
}