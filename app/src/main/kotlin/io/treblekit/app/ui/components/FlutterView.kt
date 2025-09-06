package io.treblekit.app.ui.components

import android.view.View
import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import io.treblekit.app.IViewFactory
import io.treblekit.app.R
import io.treblekit.app.ui.theme.TrebleKitTheme

@Composable
fun FlutterView(
    modifier: Modifier = Modifier,
    factory: IViewFactory? = null,
) {
    ViewFactory(
        modifier = modifier,
        factory = factory,
    ) {
        getFlutterView
    }
}

@Preview
@Composable
private fun FlutterViewPreview() {
    FlutterView()
}

@Composable
private fun ViewFactory(
    modifier: Modifier = Modifier,
    factory: IViewFactory? = null,
    view: IViewFactory.() -> View? = { null },
) {
    val inspection: Boolean = LocalInspectionMode.current
    val inspectionModeText: String = stringResource(
        id = R.string.inspection_mode_text,
    )
    AndroidView(
        factory = { context ->
            when {
                inspection -> TextView(context)
                factory?.view() != null -> factory.view()!!
                else -> View(context)
            }
        },
        modifier = modifier,
        update = { view ->
            if (inspection && view is TextView) {
                view.text = inspectionModeText
            }
        },
    )
}

@Preview
@Composable
private fun ViewFactoryPreview() {
    TrebleKitTheme {
        ViewFactory()
    }
}