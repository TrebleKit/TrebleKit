package io.treblekit.app.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun TrebleScaffold(
    modifier: Modifier = Modifier,
    background: @Composable BoxScope.() -> Unit,
    effect: @Composable BoxScope.() -> Unit,
    overlay: @Composable BoxScope.() -> Unit,
    topBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            content = background,
        )
        Box(
            modifier = Modifier.fillMaxSize(),
            content = effect,
        )
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = topBar,
            bottomBar = bottomBar,
            containerColor = Color.Transparent,
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = innerPadding),
                contentAlignment = Alignment.Center,
                content = content
            )
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            content = overlay,
        )
    }
}