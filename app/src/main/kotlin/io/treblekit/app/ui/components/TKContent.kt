package io.treblekit.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kyant.capsule.ContinuousRoundedRectangle
import io.treblekit.app.ui.navigation.EKitPage
import io.treblekit.app.ui.navigation.EbKitPage
import io.treblekit.app.ui.navigation.FeOSPage
import io.treblekit.app.ui.navigation.HomePage
import io.treblekit.app.ui.theme.AppBackgroundColor
import io.treblekit.app.ui.theme.TrebleKitTheme

@Composable
fun <T : Any> TKContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: T,
    builder: NavGraphBuilder.() -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxSize()
            .padding(all = 16.dp),
        shape = ContinuousRoundedRectangle(size = 16.dp),
        shadowElevation = 4.dp,
    ) {
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.fillMaxSize(),
            builder = builder,
        )
    }
}

@Preview
@Composable
private fun TKContentPreview() {
    TrebleKitTheme {
        TKContent(
            modifier = Modifier.background(color = AppBackgroundColor),
            navController = rememberNavController(),
            startDestination = HomePage,
        ) {
            composable<HomePage> {}
            composable<FeOSPage> {}
            composable<EKitPage> {}
            composable<EbKitPage> {}
        }
    }
}