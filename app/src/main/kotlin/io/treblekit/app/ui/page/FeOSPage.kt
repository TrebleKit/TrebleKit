package io.treblekit.app.ui.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import io.treblekit.app.ui.components.IViewFactory
import io.treblekit.app.ui.components.ViewFactory
import io.treblekit.app.ui.navigation.EKitPage
import io.treblekit.app.ui.theme.TrebleKitTheme
import io.treblekit.app.ui.utils.navigateTo

@Composable
fun FeOSPage(
    modifier: Modifier = Modifier,
    navController: NavHostController? = null,
    factory: IViewFactory? = null,
) {
    val inspection: Boolean = LocalInspectionMode.current
    val count = remember { mutableIntStateOf(value = 0) }
    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(bottom = 80.0.dp),
                onClick = {
                    if (inspection) {
                        count.intValue++
                    } else {
                        navigateTo(
                            navController = navController,
                            route = EKitPage,
                        )
                    }
                },
            ) {
                Icon(
                    imageVector = if (inspection) {
                        Icons.Filled.Add
                    } else {
                        Icons.AutoMirrored.Filled.OpenInNew
                    },
                    contentDescription = null,
                )
            }
        },
        contentWindowInsets = WindowInsets(),
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding),
            contentAlignment = Alignment.Center,
        ) {
            when {
                inspection -> Column(
                    modifier = Modifier.wrapContentSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "You have pushed the button this many times:")
                    Text(
                        text = count.intValue.toString(),
                        style = MaterialTheme.typography.headlineMedium,
                    )
                }

                else -> ViewFactory(
                    modifier = Modifier.fillMaxSize(),
                    factory = factory,
                ) {
                    getFlutterView
                }
            }
        }
    }
}

@Preview
@Composable
private fun FeOSPreview() {
    TrebleKitTheme {
        FeOSPage()
    }
}