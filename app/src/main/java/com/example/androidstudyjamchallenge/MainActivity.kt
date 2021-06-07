package com.example.androidstudyjamchallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.isFocused
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidstudyjamchallenge.ext.setContentView
import com.example.androidstudyjamchallenge.ui.theme.AndroidStudyJamChallengeTheme

class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var showSearch by mutableStateOf(false)
        setContentView("ChallengeDemo", topAppBarClickAction = {
            showSearch = !showSearch
        }) {
            Content(showSearch)
        }
    }
}

val dataSource: List<String> = arrayListOf<String>().apply {
    repeat(20) {
        add(it.toString())
    }
}

@ExperimentalAnimationApi
@Composable
fun Content(showSearch: Boolean = false) {
    val searchResults = remember {
        mutableStateOf(dataSource)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        AnimatedVisibility(
            visible = showSearch,
            enter = slideInVertically(
                initialOffsetY = { -40 }
            ) + expandVertically(
                expandFrom = Alignment.Top
            ) + fadeIn(initialAlpha = 0.3f),
            exit = slideOutVertically() + shrinkVertically() + fadeOut()
        ) {
            SearchBar(
                onQueryChange = { query ->
                    searchResults.value = dataSource.filter { it.contains(query) }
                }
            )
        }

        LazyColumn {
            items(searchResults.value) { item ->
                Text(
                    item,
                    modifier = Modifier
                        .padding(10.dp)
                        .height(35.dp)
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.primary)
                        .clip(shape = RoundedCornerShape(8.dp)),
                    color = Color.White
                )
            }
        }
    }
}

/**
 * 有状态
 */
@Composable
fun SearchBar(onQueryChange: (String) -> Unit) {
    val query = remember {
        mutableStateOf("")
    }
    val searchFocused = remember {
        mutableStateOf(false)
    }
    Search(
        query.value,
        onQueryChange = {
            query.value = it.toString()
            onQueryChange(it.toString())
        },
        searchFocused = searchFocused.value,
        onSearchFocusChange = {
            searchFocused.value = it
        }
    )
}

/**
 * 无状态
 */
@Composable
fun Search(
    query: CharSequence,
    onQueryChange: (CharSequence) -> Unit,
    searchFocused: Boolean,
    onSearchFocusChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val borderColor by animateColorAsState(if (searchFocused) MaterialTheme.colors.primary else Color(0xffD9D9D9))
    Surface(
        shape = MaterialTheme.shapes.small,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .border(1.dp, borderColor)
            .padding(horizontal = 24.dp, vertical = 8.dp)
    ) {
        Box(Modifier.fillMaxSize()) {
            if (query.isEmpty()) {
                SearchHint()
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight()
            ) {
                EditText(
                    text = query,
                    onTextChange = onQueryChange,
                    modifier = Modifier
                        .weight(1f)
                        .onFocusChanged {
                            onSearchFocusChange(it.isFocused)
                        }
                )
            }
        }
    }
}

/**
 * 编辑框
 */
@Composable
fun EditText(text: CharSequence, onTextChange: (CharSequence) -> Unit, modifier: Modifier) {
    val state = remember {
        mutableStateOf("")
    }
    state.value = text.toString()
    BasicTextField(
        textStyle = TextStyle(Color.Black),
        value = state.value,
        onValueChange = {
            state.value = it
            onTextChange(it)
        },
        maxLines = 1,
        modifier = modifier
    )
}

@Composable
private fun SearchHint() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize()
    ) {
        Icon(
            imageVector = Icons.Outlined.Search,
            contentDescription = "搜索icon"
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = "请输入搜索内容"
        )
    }
}

@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AndroidStudyJamChallengeTheme {
        Content()
    }
}