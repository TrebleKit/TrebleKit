package io.treblekit.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.kyant.liquidglass.liquidGlassProvider
import com.kyant.liquidglass.rememberLiquidGlassProviderState
import io.treblekit.app.ui.theme.Background
import kotlinx.coroutines.launch

typealias GotoPage<T> = (route: T) -> Unit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> TKScaffold(
    modifier: Modifier = Modifier,
    tabs: ArrayList<NavigationItem<T>>,
    content: @Composable (
        route: T,
        inner: PaddingValues,
        goto: GotoPage<T>,
    ) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        pageCount = { tabs.size },
        initialPage = 0,
    )
    val targetPage = remember {
        mutableIntStateOf(value = pagerState.currentPage)
    }
    val providerState = rememberLiquidGlassProviderState(
        backgroundColor = MaterialTheme.colorScheme.background
    )
    Scaffold(
        modifier = modifier,
        topBar = {
            TKTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
            )
        },
        bottomBar = {
            LiquidGlassNavigationBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                liquidGlassProviderState = providerState,
                background = Background,
                tabs = tabs,
                selectedIndexState = targetPage,
                onTabSelected = { index ->
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(page = index)
                    }
                },
            )
        },
        content = { inner ->
            HorizontalPager(
                modifier = Modifier
                    .fillMaxSize()
                    .liquidGlassProvider(state = providerState)
                    .background(color = Background),
                state = pagerState,
                userScrollEnabled = false,
                pageContent = { page ->
                    content.invoke(tabs[page].route, inner) { route ->
                        for (page in tabs) {
                            if (page.route == route) {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(
                                        page = page.page
                                    )
                                }
                            }
                        }
                    }
                },
            )
        },
    )
}
