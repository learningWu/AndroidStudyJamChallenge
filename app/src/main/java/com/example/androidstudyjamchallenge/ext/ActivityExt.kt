package com.example.androidstudyjamchallenge.ext

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext
import com.example.androidstudyjamchallenge.component.Container
import com.example.androidstudyjamchallenge.ui.theme.AndroidStudyJamChallengeTheme

/**
 * Created by wuzixuan on 2021/6/7
 */

fun ComponentActivity.setContentView(
    title: CharSequence,
    parent: CompositionContext? = null,
    topAppBarClickAction: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    setContent(parent) {
        AndroidStudyJamChallengeTheme {
            // A surface container using the 'background' color from the theme
            Surface(color = MaterialTheme.colors.background) {
                Container(title = title, onClick = topAppBarClickAction) {
                    content()
                }
            }
        }
    }
}