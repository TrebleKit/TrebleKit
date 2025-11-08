package io.treblekit.ui.components

import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import io.treblekit.R
import io.treblekit.ui.theme.TrebleKitTheme
import io.treblekit.ui.theme.androidGreen

@Composable
fun SystemTag(modifier: Modifier = Modifier) {
    var showDialog: Boolean by remember { mutableStateOf(value = false) }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
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
    }
}

@Preview
@Composable
private fun SystemTagPreview() {
    TrebleKitTheme {
        SystemTag()
    }
}