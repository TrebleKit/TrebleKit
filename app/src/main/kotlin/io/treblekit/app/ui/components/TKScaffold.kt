package io.treblekit.app.ui.components

import android.os.Build
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.treblekit.app.ui.navigation.NavigationItem
import io.treblekit.app.ui.navigation.PageList
import io.treblekit.app.ui.theme.Background
import io.treblekit.app.ui.theme.TrebleKitTheme
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
    useMaterial: Boolean = Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU,
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
            TKTopBar(useMaterial = useMaterial)
        },
        bottomBar = {
            TKNavBar(
                background = Background,
                useMaterial = useMaterial,
                pages = pages,
                selectedIndexState = targetPage,
                onTabSelected = { index ->
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(page = index)
                    }
                },
            )
        },
        containerColor = Background,
    ) { inner ->
        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
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

@Preview
@Composable
private fun TKScaffoldLiquidGlassPreview() {
    TrebleKitTheme {
        TKScaffold(
            useMaterial = false,
            pages = PageList,
        ) { _, _, _ ->

        }
    }
}

@Preview
@Composable
private fun TKScaffoldMaterialPreview() {
    TrebleKitTheme {
        TKScaffold(
            useMaterial = true,
            pages = PageList,
        ) { _, _, _ ->

        }
    }
}