package com.example.chaes.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties


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
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.onBackground,
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
        onDismissRequest = { onClickMoreDots() },
        properties = PopupProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        ),
        modifier = Modifier.background(color = MaterialTheme.colors.background)
    ) {
        DropdownMenuItem(onClick = { onClickSignOut() }) {
            Text(text = "Sign-Out")
        }
    }
}
