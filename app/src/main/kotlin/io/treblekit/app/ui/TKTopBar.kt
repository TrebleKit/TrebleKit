package io.treblekit.app.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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
import io.treblekit.app.ui.theme.capsuleHeight
import io.treblekit.app.ui.theme.capsuleRadius
import io.treblekit.app.ui.theme.capsuleWidth


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TKTopBar(modifier: Modifier = Modifier) {
    val liquidGlassProviderState = rememberLiquidGlassProviderState(
        backgroundColor = Color(color = 0xff434056)
    )
    val iconButtonLiquidGlassStyle = GlassStyle(
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
        ),
    )
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                color = Color.White,
            )
        },
        navigationIcon = {
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
        ),
    )
}