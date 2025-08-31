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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import com.kyant.liquidglass.liquidGlassProvider
import com.kyant.liquidglass.rememberLiquidGlassProviderState
import io.treblekit.app.ui.theme.Background
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

typealias GotoPage<T> = (route: T) -> Unit
typealias TKScaffoldContent<T> = @Composable (
    route: T,
    inner: PaddingValues,
    goto: GotoPage<T>,
) -> Unit

@OptIn(ExperimentalMaterial3Api::class, FlowPreview::class)
@Composable
fun <T> TKScaffold(
    modifier: Modifier = Modifier,
    pages: ArrayList<NavigationItem<T>>,
    content: TKScaffoldContent<T>,
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        pageCount = { pages.size },
        initialPage = 0,
    )
    val targetPage = remember {
        mutableIntStateOf(value = pagerState.currentPage)
    }
    val providerState = rememberLiquidGlassProviderState(
        backgroundColor = MaterialTheme.colorScheme.background
    )
    LaunchedEffect(key1 = pagerState) {
        snapshotFlow {
            pagerState.currentPage
        }.debounce(
            timeoutMillis = 150,
        ).collectLatest {
            targetPage.intValue = pagerState.currentPage
        }
    }
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TKTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
            )
        },
        bottomBar = {
            TKNavBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                liquidGlassProviderState = providerState,
                background = Background,
                tabs = pages,
                selectedIndexState = targetPage,
                onTabSelected = { index ->
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(page = index)
                    }
                },
            )
        },
    ) { inner ->
        HorizontalPager(
            modifier = Modifier
                .fillMaxSize()
                .liquidGlassProvider(state = providerState)
                .background(color = Background),
            state = pagerState,
            userScrollEnabled = false,
            pageContent = { page ->
                content.invoke(pages[page].route, inner) { route ->
                    for (page in pages) {
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
    }
}
