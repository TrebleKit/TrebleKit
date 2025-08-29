package io.treblekit.app.ui

import android.view.View
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.CompareArrows
import androidx.compose.material.icons.twotone.Dashboard
import androidx.compose.material.icons.twotone.KeyboardCommandKey
import androidx.compose.material.icons.twotone.Memory
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import io.treblekit.app.ui.theme.TrebleKitTheme
import kotlinx.serialization.Serializable

@Serializable
data object HomePage

@Serializable
data object FEOSPage

@Serializable
data object EKitPage

@Serializable
data object EbKitPage

@Composable
fun ActivityMain(flutter: View?) {
    TKScaffold(
        tabs = arrayListOf(
            NavigationItem(
                page = 0,
                route = HomePage,
                label = "主页",
                icon = Icons.TwoTone.Dashboard,
            ),
            NavigationItem(
                page = 1,
                route = FEOSPage,
                label = "feOS",
                icon = Icons.TwoTone.Memory,
            ),
            NavigationItem(
                page = 2,
                route = EKitPage,
                label = "EKit",
                icon = Icons.TwoTone.KeyboardCommandKey,
            ),
            NavigationItem(
                page = 3,
                route = EbKitPage,
                label = "EbKit",
                icon = Icons.AutoMirrored.TwoTone.CompareArrows,
            ),
        ),
    ) { route, inner, goto ->
        when (route) {
            HomePage -> HomePage(inner = inner)
            FEOSPage -> FEOSPage(inner = inner, flutter = flutter)
            EKitPage -> EKitPage(inner = inner)
            EbKitPage -> EbKitPage(inner = inner)
            else -> UnknownPage(inner = inner)
        }
    }
}

@Preview
@Composable
private fun ActivityMainPreview() {
    TrebleKitTheme {
        ActivityMain(flutter = null)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(modifier: Modifier = Modifier, inner: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = inner)
    )
}

@Composable
fun FEOSPage(modifier: Modifier = Modifier, inner: PaddingValues, flutter: View?) {
    OutlinedCard(
        modifier = modifier
            .padding(paddingValues = inner)
            .padding(all = 16.dp),
    ) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                flutter ?: View(context)
            },
        )
    }
}

@Composable
fun EKitPage(modifier: Modifier = Modifier, inner: PaddingValues) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            modifier = Modifier.padding(paddingValues = inner),
            text = "EKitPage",
            color = Color.White,
        )
    }
}

@Composable
fun EbKitPage(modifier: Modifier = Modifier, inner: PaddingValues) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            modifier = Modifier.padding(paddingValues = inner),
            text = "EbKitPage",
            color = Color.White,
        )
    }
}

@Composable
fun UnknownPage(modifier: Modifier = Modifier, inner: PaddingValues) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            modifier = Modifier.padding(paddingValues = inner),
            text = "unknown page",
            color = Color.White,
        )
    }
}
