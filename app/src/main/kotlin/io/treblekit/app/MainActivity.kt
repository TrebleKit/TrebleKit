package io.treblekit.app

import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material.icons.twotone.Memory
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.kyant.liquidglass.liquidGlassProvider
import com.kyant.liquidglass.rememberLiquidGlassProviderState
import com.wyq0918dev.flutter_mixed.FlutterMixedPlugin
import io.flutter.embedding.android.FlutterFragment
import io.treblekit.app.ui.LiquidGlassNavigationBar
import io.treblekit.app.ui.NavigationItem
import io.treblekit.app.ui.theme.TrebleKitTheme
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var mFlutterFragment: FlutterFragment? = null
    private var mFlutterView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        FlutterMixedPlugin.loadFlutter(
            activity = this@MainActivity
        ) { fragment, view ->
            mFlutterFragment = fragment
            mFlutterView = view
        }

        setContent {
            TrebleKitTheme {
                ActivityMain(flutter = mFlutterView)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class, FlowPreview::class)
@Composable
fun ActivityMain(flutter: View?) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(
        state = rememberTopAppBarState()
    )

    val coroutineScope = rememberCoroutineScope()

    val tabs = arrayListOf(
        NavigationItem(
            label = "主页",
            icon = Icons.TwoTone.Home,
        ),
        NavigationItem(
            label = "feOS",
            icon = Icons.TwoTone.Memory,
        ),
        NavigationItem(
            label = "EKit",
            icon = Icons.TwoTone.Home,
        ),
        NavigationItem(
            label = "EbKit",
            icon = Icons.TwoTone.Home,
        ),
    )

    val pagerState = rememberPagerState(
        pageCount = { tabs.size },
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

    val providerState = rememberLiquidGlassProviderState(
        backgroundColor = MaterialTheme.colorScheme.background
    )


    Scaffold(
        modifier = Modifier.fillMaxSize(),
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
//                useMaterial = true,
            )
        },
    ) { innerPadding ->
        HorizontalPager(
            modifier = Modifier
                .liquidGlassProvider(state = providerState)
                .background(color = MaterialTheme.colorScheme.background),
            state = pagerState,
            userScrollEnabled = false,
            pageContent = { page ->
                when (page) {
                    0 -> HomePage(innerPadding = innerPadding)
                    1 -> FEOSPage(innerPadding = innerPadding, flutter = flutter)
                    2 -> EKitPage(innerPadding = innerPadding)
                    3 -> EbKitPage(innerPadding = innerPadding)
                    else -> UnknownPage(innerPadding = innerPadding)
                }
            },
        )
    }
}

@Preview
@Composable
private fun ActivityMainPreview() {
    TrebleKitTheme {
        ActivityMain(flutter = null)
    }
}


@Composable
fun HomePage(modifier: Modifier = Modifier, innerPadding: PaddingValues) {

}

@Composable
fun FEOSPage(modifier: Modifier = Modifier, innerPadding: PaddingValues, flutter: View?) {
    OutlinedCard(
        modifier = modifier
            .padding(paddingValues = innerPadding)
            .padding(all = 16.dp),
    ) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                flutter ?: View(context)
            },
        )
    }
}

@Composable
fun EKitPage(modifier: Modifier = Modifier, innerPadding: PaddingValues) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            modifier = Modifier.padding(paddingValues = innerPadding),
            text = "EKitPage",
        )
    }
}

@Composable
fun EbKitPage(modifier: Modifier = Modifier, innerPadding: PaddingValues) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            modifier = Modifier.padding(paddingValues = innerPadding),
            text = "EbKitPage",
        )
    }
}

@Composable
fun UnknownPage(modifier: Modifier = Modifier, innerPadding: PaddingValues) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            modifier = Modifier.padding(paddingValues = innerPadding),
            text = "unknown page",
        )
    }
}

