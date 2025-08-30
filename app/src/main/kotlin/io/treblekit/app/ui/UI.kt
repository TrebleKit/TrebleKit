package io.treblekit.app.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.twotone.CompareArrows
import androidx.compose.material.icons.twotone.Dashboard
import androidx.compose.material.icons.twotone.KeyboardCommandKey
import androidx.compose.material.icons.twotone.Memory
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.treblekit.app.IViewFactory
import io.treblekit.app.R
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
fun ActivityMain(factory: IViewFactory? = null) {
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
        factory = factory,
    ) { route, inner, goto ->
        when (route) {
            HomePage -> HomePage(inner = inner)
            FEOSPage -> FEOSPage(inner = inner, factory = factory)
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
        ActivityMain()
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
fun FEOSPage(
    modifier: Modifier = Modifier,
    inner: PaddingValues = PaddingValues(),
    factory: IViewFactory? = null,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(inner),
    ) {
        ActionBar(
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp,
                bottom = 8.dp,
            ),
            factory = factory,
            title = {
                Text(
                    text = stringResource(
                        id = R.string.app_name,
                    ),
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = {},
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                    )
                }
            },
        )
        Flutter(
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp,
                top = 8.dp,
                bottom = 16.dp,
            ),
            factory = factory,
        )
    }
}

@Preview
@Composable
fun FEOSPreview() {
    TrebleKitTheme {
        FEOSPage()
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
