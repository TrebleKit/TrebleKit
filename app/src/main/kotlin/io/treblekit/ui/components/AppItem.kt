package io.treblekit.ui.components

import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.imageloading.rememberDrawablePainter
import com.kyant.backdrop.backdrops.LayerBackdrop
import com.kyant.backdrop.backdrops.rememberLayerBackdrop
import com.kyant.capsule.ContinuousCapsule
import io.treblekit.R
import io.treblekit.ui.theme.TrebleKitTheme
import io.treblekit.ui.utils.NoOnClick
import io.treblekit.ui.utils.backdropEffects

@Composable
fun AppItem(
    modifier: Modifier = Modifier,
    backdrop: LayerBackdrop = rememberLayerBackdrop(),
    onLaunch: () -> Unit = NoOnClick,
    appIcon: Painter,
    appName: String,
) {
    Column(
        modifier = modifier.wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(size = 60.dp)
                .backdropEffects(
                    backdrop = backdrop,
                    shape = ContinuousCapsule,
                    color = MaterialTheme.colorScheme.surfaceContainerHighest,
                    alpha = 0.8f,
                    bigBlock = false,
                )
                .clickable(onClick = onLaunch),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = appIcon,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(alpha = 0.8f)
            )
        }
        Text(
            text = appName,
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
    }
}

@Preview
@Composable
private fun AppItemPreview() {
    TrebleKitTheme {
        AppItem(
            appIcon = rememberDrawablePainter(
                drawable = AppCompatResources.getDrawable(
                    LocalContext.current,
                    R.mipmap.ic_treblekit
                )
            ),
            appName = stringResource(
                id = R.string.app_name,
            ),
        )
    }
}