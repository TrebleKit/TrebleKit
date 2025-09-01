package io.treblekit.app.ui.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.treblekit.app.ui.components.Flutter
import io.treblekit.app.ui.components.IViewFactory
import io.treblekit.app.ui.theme.Background
import io.treblekit.app.ui.theme.TrebleKitTheme

@Composable
fun FeOSPage(
    modifier: Modifier = Modifier,
    inner: PaddingValues = PaddingValues(),
    factory: IViewFactory? = null,
) {
    Flutter(
        modifier = modifier
            .padding(paddingValues = inner)
            .padding(all = 16.dp),
        factory = factory,
    )
//    Column(
//        modifier = modifier
//            .fillMaxSize()
//            .padding(paddingValues = inner),
//    ) {
//        ActionBar(
//            modifier = Modifier.padding(
//                start = 16.dp,
//                end = 16.dp,
//                top = 16.dp,
//                bottom = 8.dp,
//            ),
//            factory = factory,
//            title = {
//                Text(
//                    text = stringResource(
//                        id = R.string.app_name,
//                    ),
//                )
//            },
//            navigationIcon = {
//                IconButton(
//                    onClick = {},
//                ) {
//                    Icon(
//                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                        contentDescription = null,
//                    )
//                }
//            },
//        )
//        Flutter(
//            modifier = Modifier.padding(
//                start = 16.dp,
//                end = 16.dp,
//                top = 8.dp,
//                bottom = 16.dp,
//            ),
//            factory = factory,
//        )
//    }
}

@Preview
@Composable
private fun FeOSPreview() {
    TrebleKitTheme {
        FeOSPage(
            modifier = Modifier.background(
                color = Background,
            ),
        )
    }
}