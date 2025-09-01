package io.treblekit.app.ui.activity

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import io.treblekit.app.ui.components.IViewFactory
import io.treblekit.app.ui.components.TKScaffold
import io.treblekit.app.ui.navigation.EKitPage
import io.treblekit.app.ui.navigation.EbKitPage
import io.treblekit.app.ui.navigation.FeOSPage
import io.treblekit.app.ui.navigation.HomePage
import io.treblekit.app.ui.navigation.PageList
import io.treblekit.app.ui.page.EKitPage
import io.treblekit.app.ui.page.EbKitPage
import io.treblekit.app.ui.page.FeOSPage
import io.treblekit.app.ui.page.HomePage
import io.treblekit.app.ui.page.UnknownPage
import io.treblekit.app.ui.theme.TrebleKitTheme

@Composable
fun ActivityMain(factory: IViewFactory? = null) {
    TKScaffold(pages = PageList) { route, inner, goto ->
        when (route) {
            HomePage -> HomePage(inner = inner, factory = factory)
            FeOSPage -> FeOSPage(inner = inner, factory = factory)
            EKitPage -> EKitPage(inner = inner)
            EbKitPage -> EbKitPage(inner = inner)
            else -> UnknownPage(inner = inner)
        }
    }
}

@Preview(
    name = "ActivityMain",
    device = "spec:width=1080px,height=2424px,isRound=true",
    showSystemUi = true,
    showBackground = true,
    fontScale = 1.0f,
    wallpaper = Wallpapers.BLUE_DOMINATED_EXAMPLE,
)
@Composable
private fun ActivityMainPreview() {
    TrebleKitTheme {
        ActivityMain()
    }
}