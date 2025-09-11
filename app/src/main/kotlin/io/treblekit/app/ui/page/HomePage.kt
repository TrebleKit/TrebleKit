package io.treblekit.app.ui.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Extension
import androidx.compose.material.icons.outlined.Settings
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
import io.treblekit.app.ui.utils.NullableController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavHostController?.HomePage(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Home")
                },
                expandedHeight = ToolbarHeight,
                windowInsets = WindowInsets()
            )
        },
        bottomBar = {
            NavigationBar(
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
                            imageVector = Icons.Outlined.Extension,
                            contentDescription = null,
                        )
                    },
                    label = {
                        Text(text = "Extension")
                    },
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { },
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Settings,
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
    ) { innerPadding ->
        Column(modifier = Modifier.padding(paddingValues = innerPadding)) {
//            ActionBar(
//                modifier = Modifier.padding(
//                    start = 16.dp,
//                    end = 16.dp,
//                    top = 16.dp,
//                    bottom = 8.dp,
//                ),
//                factory = factory,
//                title = {
//                    Text(
//                        text = stringResource(
//                            id = R.string.app_name,
//                        ),
//                    )
//                },
//                navigationIcon = {
//                    IconButton(
//                        onClick = {},
//                    ) {
//                        Icon(
//                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                            contentDescription = null,
//                        )
//                    }
//                },
//            )
        }
    }
}

@Preview
@Composable
private fun HomePagePreview() {
    TrebleKitTheme {
        NullableController {
            HomePage()
        }
    }
}