package io.treblekit.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.kyant.liquidglass.liquidGlassProvider
import com.kyant.liquidglass.rememberLiquidGlassProviderState
import kotlinx.coroutines.launch

@Composable
fun LiquidGlassScaffold(
    modifier: Modifier = Modifier,
    tabs: ArrayList<NavigationItem>,
    content: @Composable (page: Int, inner: PaddingValues) -> Unit,
) {

    val coroutineScope = rememberCoroutineScope()


    val pagerState = rememberPagerState(
        pageCount = { tabs.size },
        initialPage = 0,
    )

    val targetPage = remember {
        mutableIntStateOf(value = pagerState.currentPage)
    }

//    LaunchedEffect(key1 = pagerState) {
//        snapshotFlow {
//            pagerState.currentPage
//        }.debounce(
//            timeoutMillis = 150,
//        ).collectLatest {
//            targetPage.intValue = pagerState.currentPage
//        }
//    }

    val providerState = rememberLiquidGlassProviderState(
        backgroundColor = MaterialTheme.colorScheme.background
    )

//    LazyColumn {
//        item {
//            Box(modifier = Modifier.fillParentMaxSize())
//        }
//    }


    Scaffold(
        modifier = modifier,
        bottomBar = {
            LiquidGlassNavigationBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                liquidGlassProviderState = providerState,
                tabs = tabs,
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
                .liquidGlassProvider(state = providerState)
                .background(color = MaterialTheme.colorScheme.background),
            state = pagerState,
            userScrollEnabled = false,
            pageContent = { page ->
                content.invoke(page, inner)
            },
        )
    }
}