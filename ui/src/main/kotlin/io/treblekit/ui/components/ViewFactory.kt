package io.treblekit.ui.components

import android.view.View
import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import io.treblekit.ui.R
import io.treblekit.ui.factory.IViewFactory
import io.treblekit.ui.theme.TrebleKitTheme
import io.treblekit.ui.factory.LocalViewFactory

@Composable
fun ViewFactory(
    modifier: Modifier = Modifier,
    view: IViewFactory.() -> View? = { null },
) {
    val inspection: Boolean = LocalInspectionMode.current
    val factory: IViewFactory? = LocalViewFactory.current
    val prompt: String = stringResource(
        id = R.string.inspection_mode_text,
    )
    AndroidView(
        factory = { context ->
            when {
                inspection -> TextView(context)

                factory != null -> factory.view() ?: error(
                    message = "FactoryHost not implemented",
                )

                else -> View(context)
            }
        },
        modifier = modifier,
        update = { view ->
            if (inspection && view is TextView) {
                view.text = prompt
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun ViewFactoryPreview() {
    TrebleKitTheme {
        ViewFactory()
    }
}