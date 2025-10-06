package io.treblekit.app.ui.components

import android.app.Activity
import android.view.View
import android.widget.TextView
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import io.treblekit.app.R
import io.treblekit.app.common.FactoryHost
import io.treblekit.app.common.IViewFactory
import io.treblekit.app.ui.theme.TrebleKitTheme

@Composable
fun ViewFactory(
    modifier: Modifier = Modifier,
    view: IViewFactory.() -> View? = { null },
) {
    val activity: Activity? = LocalActivity.current
    val inspection: Boolean = LocalInspectionMode.current
    val inspectionModeText: String = stringResource(
        id = R.string.inspection_mode_text,
    )
    AndroidView(
        factory = { context ->
            when {
                inspection -> TextView(context)
                activity != null && activity is FactoryHost -> {
                    (activity as? FactoryHost)?.getViewFactory?.view() ?: error(
                        message = "FactoryHost not implemented",
                    )
                }

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

@Preview(showBackground = true)
@Composable
private fun ViewFactoryPreview() {
    TrebleKitTheme {
        ViewFactory()
    }
}