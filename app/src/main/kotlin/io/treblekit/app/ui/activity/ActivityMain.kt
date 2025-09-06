package io.treblekit.app.ui.activity

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.navigation.compose.composable
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
import io.treblekit.app.ui.theme.TrebleKitTheme

@Composable
fun ActivityMain(factory: IViewFactory? = null) {
    TKScaffold(pages = PageList, useMaterial = false) { navController ->
        composable<HomePage> {
            HomePage(factory = factory)
        }
        composable<FeOSPage> {
            FeOSPage(factory = factory, navController = navController)
        }
        composable<EKitPage>{
            EKitPage(navController = navController)
        }
        composable<EbKitPage> {
            EbKitPage()
        }
    }
//    TKScaffold(pages = PageList, useMaterial = false) { route, goto ->
//        when (route) {
//            HomePage -> HomePage(factory = factory)
//            FeOSPage -> FeOSPage(factory = factory, goto = goto)
//            EKitPage -> EKitPage(goto = goto)
//            EbKitPage -> EbKitPage()
//            else -> UnknownPage()
//        }
//    }
}

@Preview(
    name = "ActivityMain",
    device = "spec:width=1080px,height=2424px",
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