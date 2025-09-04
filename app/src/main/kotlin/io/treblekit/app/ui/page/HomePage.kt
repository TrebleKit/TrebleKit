package io.treblekit.app.ui.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.treblekit.app.R
import io.treblekit.app.ui.components.ActionBar
import io.treblekit.app.ui.components.IViewFactory
import io.treblekit.app.ui.theme.Background
import io.treblekit.app.ui.theme.TrebleKitTheme

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    inner: PaddingValues = PaddingValues(),
    factory: IViewFactory? = null,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues = inner)
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
    }
}

@Preview
@Composable
private fun HomePagePreview() {
    TrebleKitTheme {
        HomePage(
            modifier = Modifier.background(
                color = Background,
            ),
        )
    }
}