package io.treblekit.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kyant.liquidglass.GlassStyle
import com.kyant.liquidglass.liquidGlass
import com.kyant.liquidglass.material.GlassMaterial
import com.kyant.liquidglass.refraction.InnerRefraction
import com.kyant.liquidglass.refraction.RefractionAmount
import com.kyant.liquidglass.refraction.RefractionHeight
import com.kyant.liquidglass.rememberLiquidGlassProviderState
import com.kyant.liquidglass.shadow.GlassShadow
import io.treblekit.app.R
import io.treblekit.app.ui.theme.AppBackgroundColor
import io.treblekit.app.ui.theme.CapsuleEdgePadding
import io.treblekit.app.ui.theme.CapsuleHeight
import io.treblekit.app.ui.theme.CapsuleIndent
import io.treblekit.app.ui.theme.CapsuleRadius
import io.treblekit.app.ui.theme.CapsuleWidth
import io.treblekit.app.ui.theme.TrebleKitTheme
import io.treblekit.app.ui.utils.NoOnClick

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TKTopBar(
    modifier: Modifier = Modifier,
    useLiquidGlass: Boolean,
) {
    CenterAlignedTopAppBar(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                color = Color.White,
            )
        },
        navigationIcon = {
            Box(
                modifier = Modifier
                    .padding(start = CapsuleEdgePadding)
                    .width(width = CapsuleWidth)
                    .height(height = CapsuleHeight)
                    .topBarButtonStyle(
                        style = getStyle(useLiquidGlass = useLiquidGlass),
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
                        tint = Color(color = 0xff8E8E9E)
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
            Row(
                modifier = Modifier
                    .padding(end = CapsuleEdgePadding)
                    .height(height = CapsuleHeight)
                    .width(width = CapsuleWidth)
                    .topBarButtonStyle(
                        style = getStyle(useLiquidGlass = useLiquidGlass),
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .weight(weight = 1f)
                        .fillMaxSize()
                        .clickable(onClick = NoOnClick),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Filled.MoreHoriz,
                        contentDescription = null,
                        tint = Color(color = 0xff8E8E9E),
                    )
                }
                VerticalDivider(
                    modifier = Modifier
                        .padding(vertical = CapsuleIndent)
                        .wrapContentWidth()
                        .fillMaxHeight(),
                    color = Color(color = 0xff8E8E9E),
                )
                Box(
                    modifier = Modifier
                        .weight(weight = 1f)
                        .fillMaxSize()
                        .clickable(onClick = NoOnClick),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = null,
                        tint = Color(color = 0xff8E8E9E),
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
        ),
    )
}

private fun getStyle(useLiquidGlass: Boolean): TopBarButtonStyle {
    return if (useLiquidGlass) {
        TopBarButtonStyle.LiquidGlass
    } else {
        TopBarButtonStyle.Material3
    }
}

private enum class TopBarButtonStyle {
    LiquidGlass, Material3
}

@Stable
@Composable
private fun Modifier.topBarButtonStyle(style: TopBarButtonStyle): Modifier {
    val providerState = rememberLiquidGlassProviderState(
        backgroundColor = Color(color = 0xff434056)
    )
    val buttonStyle = GlassStyle(
        shape = RoundedCornerShape(percent = CapsuleRadius),
        innerRefraction = InnerRefraction(
            height = RefractionHeight(value = 8.dp),
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
        ),
    )
    return this then when (style) {
        TopBarButtonStyle.LiquidGlass -> Modifier.liquidGlass(
            state = providerState,
            style = buttonStyle,
        )

        TopBarButtonStyle.Material3 -> Modifier
            .clip(shape = RoundedCornerShape(percent = 50))
            .background(color = Color(color = 0xff434056))
    }
}

@Preview
@Composable
private fun TKTopBarLiquidGlassPreview() {
    TrebleKitTheme {
        TKTopBar(
            modifier = Modifier.background(
                color = AppBackgroundColor,
            ),
            useLiquidGlass = true,
        )
    }
}

@Preview
@Composable
private fun TKTopBarMaterialPreview() {
    TrebleKitTheme {
        TKTopBar(
            modifier = Modifier.background(
                color = AppBackgroundColor,
            ),
            useLiquidGlass = false,
        )
    }
}