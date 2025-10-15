package io.treblekit.app.ui.destination

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.twotone.Category
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kyant.capsule.ContinuousRoundedRectangle
import io.treblekit.app.R
import io.treblekit.app.ui.components.FlutterView
import io.treblekit.app.ui.theme.TrebleKitTheme
import io.treblekit.app.ui.utils.NoOnClick

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlatformDestination(
    modifier: Modifier = Modifier,
    animateToDashboard: () -> Unit = NoOnClick,
) {
    val inspection: Boolean = LocalInspectionMode.current
    var dropdownExpanded: Boolean by remember { mutableStateOf(value = false) }
    var aboutExpanded: Boolean by remember { mutableStateOf(value = false) }
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    top = 16.dp,
                    end = 16.dp,
                    bottom = 8.dp,
                )
                .height(height = 56.dp),
            shape = ContinuousRoundedRectangle(size = 16.dp),
        ) {
            TopAppBar(
                title = {
                    Text(text = "Treble平台")
                },
                modifier = Modifier.fillMaxWidth(),
                navigationIcon = {
                    IconButton(
                        onClick = animateToDashboard,
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    if (aboutExpanded) AlertDialog(
                        onDismissRequest = {
                            aboutExpanded = false
                        },
                        title = {
                            Text(text = "关于Treble平台")
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.TwoTone.Category,
                                contentDescription = null,
                            )
                        },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    aboutExpanded = false
                                },
                            ) {
                                Text(text = "确定")
                            }
                        },
                        text = {
                            Text(text = "Treble平台是基于Dart和Kotlin自研的核心软件平台. 由FreeFEOS, EcosedKit和EbKit三大部分组成. 分别是核心组件, 应用层组件和平台能力桥接组件.")
                        },
                    )
                    DropdownMenu(
                        expanded = dropdownExpanded,
                        onDismissRequest = {
                            dropdownExpanded = false
                        },
                    ) {
                        DropdownMenuItem(
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.Info,
                                    contentDescription = null,
                                )
                            },
                            text = {
                                Text(text = "关于")
                            },
                            onClick = {
                                aboutExpanded = true
                                dropdownExpanded = false
                            },
                        )
                    }
                    IconButton(
                        onClick = {
                            dropdownExpanded = true
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = null,
                        )
                    }
                },
                windowInsets = WindowInsets(),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
            )
        }
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 16.dp,
                    top = 8.dp,
                    end = 16.dp,
                    bottom = 16.dp,
                ),
            shape = ContinuousRoundedRectangle(size = 16.dp),
        ) {
            if (!inspection) {
                FlutterView()
            } else {
                Scaffold(
                    contentWindowInsets = WindowInsets(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = stringResource(id = R.string.app_name))
                            },
                            windowInsets = WindowInsets()
                        )
                    },
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues = innerPadding),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(text = stringResource(id = R.string.inspection_mode_text))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun PlatformDestinationPreview() {
    TrebleKitTheme {
        PlatformDestination()
    }
}