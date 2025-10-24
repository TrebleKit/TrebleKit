package io.treblekit.ui.destination

import android.os.Build
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.kyant.backdrop.Backdrop
import com.kyant.backdrop.backdrops.rememberLayerBackdrop
import com.kyant.backdrop.drawBackdrop
import com.kyant.backdrop.effects.blur
import com.kyant.backdrop.effects.lens
import com.kyant.backdrop.effects.vibrancy
import com.kyant.capsule.ContinuousRoundedRectangle
import io.treblekit.R
import io.treblekit.ui.components.CapsuleSpacer
import io.treblekit.ui.components.NavBlock
import io.treblekit.ui.components.SearchCapsule
import io.treblekit.ui.navigation.DashboardDestination
import io.treblekit.ui.navigation.PlatformDestination
import io.treblekit.ui.navigation.appDestination
import io.treblekit.ui.theme.androidGreen
import io.treblekit.ui.theme.capsuleEdgePadding
import io.treblekit.ui.theme.topBarPaddingExcess
import io.treblekit.ui.utils.NoOnClick

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeDestination(
    modifier: Modifier = Modifier,
    navController: NavHostController? = null,
    backdrop: Backdrop = rememberLayerBackdrop(),
) {
    val pageState: PagerState = rememberPagerState { appDestination.size }
    var showDialog: Boolean by remember { mutableStateOf(value = false) }
    val primaryContainerTint: Color = MaterialTheme.colorScheme.primaryContainer
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                },
                navigationIcon = {
                    SearchCapsule(
                        modifier = Modifier.padding(
                            start = capsuleEdgePadding - topBarPaddingExcess,
                        ),
                        backdrop = backdrop,
                        onClick = {}
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
                    if (showDialog) AlertDialog(
                        onDismissRequest = {
                            showDialog = false
                        },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    showDialog = false
                                },
                            ) {
                                Text(text = "确定")
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.Android,
                                contentDescription = null,
                            )
                        },
                        iconContentColor = androidGreen,
                        title = {
                            Text(text = "Android")
                        },
                        text = {
                            Text(text = "Android API ${Build.VERSION.SDK_INT}")
                        },
                    )
                    IconButton(
                        onClick = {
                            showDialog = true
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Android,
                            contentDescription = null,
                            tint = androidGreen,
                        )
                    }
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        text = stringResource(id = R.string.android),
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Left,
                    )
                },
                floatingActionButton = {
                    Row {
                        NavBlock(
                            modifier = Modifier.padding(end = 4.dp),
                            pageState = pageState,
                            backdrop = backdrop,
                        )
                        ExtendedFloatingActionButton(
                            text = {
                                Text(
                                    text = "返回",
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            },
                            icon = {
                                Icon(
                                    imageVector = Icons.TwoTone.Home,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            },
                            onClick = NoOnClick,
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(start = 4.dp)
                                .drawBackdrop(
                                    backdrop = backdrop,
                                    shape = {
                                        ContinuousRoundedRectangle(size = 16.dp)
                                    },
                                    effects = {
                                        vibrancy()
                                        blur(radius = 2f.dp.toPx())
                                        lens(
                                            refractionHeight = 12f.dp.toPx(),
                                            refractionAmount = 24f.dp.toPx(),
                                        )
                                    },
                                    onDrawSurface = {
                                        drawRect(
                                            color = primaryContainerTint.copy(alpha = 0.8f),
                                            blendMode = BlendMode.Hue,
                                        )
                                    },
                                ),
                            containerColor = Color.Transparent,
                            elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
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
                    navController = navController,
                    pageState = pageState,
                    backdrop = backdrop,
                )

                else -> UnknownDestination()
            }
        }
    }
}