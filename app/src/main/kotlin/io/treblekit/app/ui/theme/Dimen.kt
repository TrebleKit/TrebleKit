package io.treblekit.app.ui.theme

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// 胶囊宽度
val capsuleWidth: Dp = 87.dp

// 胶囊高度
val capsuleHeight: Dp = 32.dp

// 胶囊右边距
val capsuleEdgePadding: Dp = 16.dp

// 胶囊圆角半径
val capsuleRadius: Dp = 20.dp

val capsuleStrokeWidth: Dp = 1.dp
val capsuleIndent: Dp = 5.dp


// ActionBar 默认高度
@OptIn(ExperimentalMaterial3Api::class)
val actionBarExpandedHeight: Dp = TopAppBarDefaults.TopAppBarExpandedHeight

val bannerWidth = 16.dp // 角标宽度
val bannerDistanceOriginPointLength = 55.dp // 角标距离原点长度

val bannerTextSize: TextUnit = 10.sp

val topBarPaddingExcess: Dp = 4.dp