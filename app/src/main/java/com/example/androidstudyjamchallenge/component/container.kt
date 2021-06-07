package com.example.androidstudyjamchallenge.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Created by wuzixuan on 2021/6/7
 */

@Composable
fun Container(title: CharSequence, onClick: (() -> Unit)? = null, content: @Composable () -> Unit) {
    // Scaffold 留出内容区域
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(text = title.toString(), maxLines = 2)
            },
            actions = {
                if (onClick != null) {
                    IconButton(onClick = onClick) {
                        Icon(Icons.Filled.Search, contentDescription = null)
                    }
                }
            }
        )
    }) { innerPadding ->
        BodyContent(modifier = Modifier.padding(innerPadding)) {
            content()
        }
    }

}

@Composable
fun BodyContent(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Column(modifier = modifier.padding(8.dp)) {
        content()
    }
}