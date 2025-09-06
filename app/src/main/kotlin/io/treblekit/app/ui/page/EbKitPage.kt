package io.treblekit.app.ui.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import io.treblekit.app.ui.theme.ToolbarHeight
import io.treblekit.app.ui.theme.TrebleKitTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EbKitPage(
    modifier: Modifier = Modifier,
    navController: NavHostController? = null,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "EbKit")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                expandedHeight = ToolbarHeight,
                windowInsets = WindowInsets(),
            )
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                windowInsets = WindowInsets(),
            ) {
                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = null,
                        )
                    },
                    label = {
                        Text(text = "Home")
                    },
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = null,
                        )
                    },
                    label = {
                        Text(text = "Settings")
                    },
                )
            }
        },
        contentWindowInsets = WindowInsets(),
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = padding)
        ) {

        }
    }
}

@Preview
@Composable
private fun EbKitPagePreview() {
    TrebleKitTheme {
        EbKitPage()
    }
}