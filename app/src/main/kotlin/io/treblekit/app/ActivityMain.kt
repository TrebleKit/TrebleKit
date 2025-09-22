package io.treblekit.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityMain(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier.fillMaxSize(), topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
            )
        },
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) { innerPadding ->
        FlutterView(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding)
        )
    }
}