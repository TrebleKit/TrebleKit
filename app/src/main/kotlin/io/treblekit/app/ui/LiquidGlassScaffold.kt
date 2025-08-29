package io.treblekit.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kyant.liquidglass.GlassStyle
import com.kyant.liquidglass.liquidGlass
import com.kyant.liquidglass.liquidGlassProvider
import com.kyant.liquidglass.material.GlassMaterial
import com.kyant.liquidglass.refraction.InnerRefraction
import com.kyant.liquidglass.refraction.RefractionAmount
import com.kyant.liquidglass.refraction.RefractionHeight
import com.kyant.liquidglass.rememberLiquidGlassProviderState
import com.kyant.liquidglass.shadow.GlassShadow
import io.treblekit.app.ui.theme.capsuleHeight
import io.treblekit.app.ui.theme.capsuleRadius
import io.treblekit.app.ui.theme.capsuleWidth
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
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
    val providerState = rememberLiquidGlassProviderState(
        backgroundColor = MaterialTheme.colorScheme.background
    )
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = tabs[targetPage.intValue].label,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    val liquidGlassProviderState = rememberLiquidGlassProviderState(
                        backgroundColor = Color(color = 0xff434056)
                    )

                    val iconButtonLiquidGlassStyle =
                        GlassStyle(
                            shape = RoundedCornerShape(
                                size = capsuleRadius,
                            ),
                            innerRefraction = InnerRefraction(
                                height = RefractionHeight(
                                    value = 8.dp,
                                ),
                                amount = RefractionAmount.Full,
                            ),
                            material = GlassMaterial(
                                brush = SolidColor(
                                    value = Color(
                                        color = 0xff434056
                                    ).copy(
                                        alpha = 0.5f
                                    ),
                                ),
                            ),
                            shadow = GlassShadow(
                                elevation = 4.dp,
                                brush = SolidColor(
                                    value = Color.Black.copy(
                                        alpha = 0.15f,
                                    ),
                                ),
                            )
                        )

                    Box(
                        modifier = Modifier
                            .padding(start = (16 - 4).dp)
                            .width(width = capsuleWidth)
                            .height(height = capsuleHeight)
                            .liquidGlass(
                                state = liquidGlassProviderState,
                                style = iconButtonLiquidGlassStyle,
                            )
                            .clickable(onClick = {}),
                        contentAlignment = Alignment.Center,
                    ) {
                        Row(
                            modifier = Modifier.wrapContentSize(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = null,
                                modifier = Modifier.size(size = 20.dp),
                                tint = Color(color = 0xff8E8E9E),
                            )
                            Text(
                                text = "搜索",
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(start = 6.dp),
                                fontSize = 13.sp,
                                color = Color(color = 0xff8E8E9E),
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                },
                actions = {
//                    Spacer(
//                        modifier = Modifier.padding(
//                            paddingValues = rememberCapsulePadding(
//                                factory = factory,
//                                excess = 4.dp, // TopAppBar 自带 4dp 右边距
//                            ),
//                        ),
//                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                )
            )
        },
        bottomBar = {
            LiquidGlassNavigationBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                liquidGlassProviderState = providerState,
                background = Color(color = 0xff1B1B2B),
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
                .fillMaxSize()
                .liquidGlassProvider(state = providerState)
                .background(color = Color(color = 0xff1B1B2B)),
            state = pagerState,
            userScrollEnabled = false,
            pageContent = { page ->
                content.invoke(page, inner)
            },
        )
    }
}