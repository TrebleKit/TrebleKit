package io.treblekit.app.ui.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import io.treblekit.app.ui.components.IViewFactory
import io.treblekit.app.ui.theme.ToolbarHeight
import io.treblekit.app.ui.theme.TrebleKitTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    navController: NavHostController? = null,
    factory: IViewFactory? = null,
) {
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
        HomePage()
    }
}