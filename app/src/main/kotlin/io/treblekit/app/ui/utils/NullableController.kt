package io.treblekit.app.ui.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun NullableController(
    modifier: Modifier = Modifier,
    content: @Composable NavHostController?.() -> Unit,
) {
    Box(modifier = modifier) { null.content() }
}
