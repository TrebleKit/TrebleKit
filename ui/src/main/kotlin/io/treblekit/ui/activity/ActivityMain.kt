package io.treblekit.ui.activity

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.treblekit.ui.R
import io.treblekit.ui.components.AppBackground
import io.treblekit.ui.components.CapsuleSearch
import io.treblekit.ui.components.CapsuleSpacer
import io.treblekit.ui.components.HomeFAB
import io.treblekit.ui.components.NavBlock
import io.treblekit.ui.components.OverlayLayer
import io.treblekit.ui.components.SystemTag
import io.treblekit.ui.destination.DashboardDestination
import io.treblekit.ui.destination.PlatformDestination
import io.treblekit.ui.destination.UnknownDestination
import io.treblekit.ui.navigation.DashboardDestination
import io.treblekit.ui.navigation.PlatformDestination
import io.treblekit.ui.navigation.appDestination
import io.treblekit.ui.preview.ActivityPreview
import io.treblekit.ui.theme.TrebleKitTheme
import io.treblekit.ui.theme.capsuleEdgePadding
import io.treblekit.ui.theme.topBarPaddingExcess
import io.treblekit.ui.utils.getIndexWithRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityMain() {
    val pageState: PagerState = rememberPagerState(
        initialPage = getIndexWithRoute(route = DashboardDestination),
        pageCount = { return@rememberPagerState appDestination.size }
    )
    AppBackground { backdrop ->
        OverlayLayer(backdrop = backdrop) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    CenterAlignedTopAppBar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        title = {
                            Text(
                                text = stringResource(id = R.string.activity_main_title),
                                color = MaterialTheme.colorScheme.onBackground,
                            )
                        },
                        navigationIcon = {
                            CapsuleSearch(
                                modifier = Modifier.padding(
                                    start = capsuleEdgePadding - topBarPaddingExcess,
                                ),
                                backdrop = backdrop,
                                onClick = {},
                            )
                        },
                        actions = {
                            CapsuleSpacer()
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent,
                        ),
                    )
                },
                bottomBar = {
                    BottomAppBar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        actions = {
                            SystemTag(
                                modifier = Modifier.padding(end = 8.dp),
                            )
                        },
                        floatingActionButton = {
                            Row(
                                modifier = Modifier.wrapContentSize(),
                                horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
                            ) {
                                NavBlock(
                                    pageState = pageState,
                                    backdrop = backdrop,
                                )
                                val context = LocalContext.current
                                HomeFAB(
                                    backdrop = backdrop,
                                    onClick = {
                                        val intent = Intent().apply {
                                            action = Intent.ACTION_MAIN
                                            addCategory(Intent.CATEGORY_HOME)
                                            setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                        }
                                        context.startActivity(intent)
                                    }
                                )
                            }
                        },
                        containerColor = Color.Transparent,
                    )
                },
                containerColor = Color.Transparent,
            ) { innerPadding ->
                HorizontalPager(
                    state = pageState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues = innerPadding),
                    userScrollEnabled = false,
                ) { page ->
                    when (appDestination[page].route) {
                        DashboardDestination -> DashboardDestination(
                            pageState = pageState,
                            backdrop = backdrop,
                        )

                        PlatformDestination -> PlatformDestination(
                            pageState = pageState,
                            backdrop = backdrop,
                        )

                        else -> UnknownDestination()
                    }
                }
            }
        }
    }
}

@ActivityPreview
@Composable
private fun ActivityMainPreview() {
    TrebleKitTheme {
        ActivityMain()
    }
}