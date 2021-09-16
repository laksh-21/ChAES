package com.example.chaes.ui.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview
@Composable
fun TitleBar(
    menuOpened: Boolean = false,
    onClickMoreDots: () -> Unit = {},
    onClickSignOut: () -> Unit = {},
    userName: String = "User"
){
    TopAppBar(
        title = {
            Text(text = "Welcome, $userName")
        },
        actions = {
            Row {
                Box {
                    IconButton(onClick = { onClickMoreDots() }) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Menu")
                    }
                    HomeScreenMenu(
                        menuOpened = menuOpened,
                        onClickMoreDots = onClickMoreDots,
                        onClickSignOut = onClickSignOut,
                    )
                }
            }
        },
        elevation = 0.dp,
    )
}

@Composable
fun HomeScreenMenu(
    menuOpened: Boolean,
    onClickMoreDots: () -> Unit,
    onClickSignOut: () -> Unit,
){
    DropdownMenu(
        expanded = menuOpened,
        onDismissRequest = { onClickMoreDots() }
    ) {
        DropdownMenuItem(onClick = { onClickSignOut() }) {
            Text(text = "Sign-Out")
        }
    }
}
