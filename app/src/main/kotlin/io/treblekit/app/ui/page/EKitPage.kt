package io.treblekit.app.ui.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Apps
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import io.treblekit.app.R
import io.treblekit.app.ui.components.GotoPage
import io.treblekit.app.ui.navigation.FeOSPage
import io.treblekit.app.ui.theme.Background
import io.treblekit.app.ui.theme.TrebleKitTheme
import io.treblekit.app.ui.utils.NoOnClick

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EKitPage(
    modifier: Modifier = Modifier,
    inner: PaddingValues = PaddingValues(),
    goto: GotoPage<FeOSPage> = { FeOSPage },
) {
    val context = LocalContext.current
    val inspection = LocalInspectionMode.current
    Surface(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues = inner)
            .padding(all = 16.dp),
        shape = MaterialTheme.shapes.medium,
    ) {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(text = "EKit")
                    },
                    windowInsets = WindowInsets(),
                )
            },
            bottomBar = {
                NavigationBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    windowInsets = WindowInsets(),
                ) {
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.Home,
                                contentDescription = null,
                            )
                        },
                        label = {
                            Text(text = "首页")
                        },
                        selected = true,
                        onClick = {

                        },
                    )
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = Icons.Outlined.Apps,
                                contentDescription = null,
                            )
                        },
                        label = {
                            Text(text = "首页")
                        },
                        selected = false,
                        onClick = {

                        },
                    )
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = Icons.Outlined.AccountCircle,
                                contentDescription = null,
                            )
                        },
                        label = {
                            Text(text = "首页")
                        },
                        selected = false,
                        onClick = {

                        },
                    )

                }
            },
            contentWindowInsets = WindowInsets(),
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = padding)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 22.dp, top = 16.dp, end = 22.dp, bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = if (inspection) painterResource(
                            id = R.drawable.baseline_preview_24,
                        ) else rememberDrawablePainter(
                            drawable = ContextCompat.getDrawable(
                                context, R.mipmap.ic_ecosedkit
                            ),
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .size(size = 58.dp)
                            .clip(shape = CardDefaults.shape),
                    )
                    Text(
                        text = "EcosedKit",
                        modifier = Modifier.padding(start = 16.dp),
                        style = MaterialTheme.typography.titleLarge,
                    )
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(all = 8.dp)
                            .clip(shape = RoundedCornerShape(size = 5.dp)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "JC-ULTRA",
                            modifier = Modifier
                                .background(color = Color.Black)
                                .padding(vertical = 2.dp, horizontal = 4.dp),
                            color = Color.White,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                StateCard(
                    modifier = Modifier.padding(
                        start = 16.dp,
                        top = 8.dp,
                        end = 16.dp,
                        bottom = 8.dp,
                    ),
                    color = MaterialTheme.colorScheme.primaryContainer,
                    onClick = NoOnClick,
                    icon = Icons.Filled.CheckCircleOutline,
                    title = "一切正常",
                    subTitleTop = "AppID: xxxxxxx",
                    subTitleBottom = "版本: 1.0",
                )


                StateCard(
                    modifier = Modifier.padding(
                        start = 16.dp,
                        top = 8.dp,
                        end = 16.dp,
                        bottom = 8.dp,
                    ),
                    color = MaterialTheme.colorScheme.surfaceContainerHigh,
                    onClick = {
                        goto(FeOSPage)
                    },
                    icon = Icons.AutoMirrored.Filled.OpenInNew,
                    title = "feOS",
                    subTitleTop = "FreeFEOS Connect",
                    subTitleBottom = "点击跳转feOS",
                )


            }
        }
    }
}

@Preview
@Composable
private fun EKitPagePreview() {
    TrebleKitTheme {
        EKitPage(
            modifier = Modifier.background(
                color = Background,
            ),
        )
    }
}

@Composable
fun StateCard(
    modifier: Modifier = Modifier,
    color: Color,
    onClick: () -> Unit,
    icon: ImageVector,
    title: String,
    subTitleTop: String,
    subTitleBottom: String
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(
            containerColor = color,
        ),
        onClick = onClick,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.padding(start = 16.dp),
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(all = 16.dp)
            ) {
                Text(
                    text = title,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(Modifier.height(height = 2.dp))
                Text(
                    text = subTitleTop,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Medium,
                )
                Text(
                    text = subTitleBottom,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Medium,
                )
            }
        }
    }
}