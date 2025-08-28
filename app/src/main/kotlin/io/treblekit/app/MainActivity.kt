package io.treblekit.app

import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material.icons.twotone.Memory
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.wyq0918dev.flutter_mixed.FlutterMixedPlugin
import io.flutter.embedding.android.FlutterFragment
import io.treblekit.app.ui.LiquidGlassScaffold
import io.treblekit.app.ui.NavigationItem
import io.treblekit.app.ui.theme.TrebleKitTheme

class MainActivity : AppCompatActivity() {

    private var mFlutterFragment: FlutterFragment? = null
    private var mFlutterView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        FlutterMixedPlugin.loadFlutter(
            activity = this@MainActivity
        ) { fragment, view ->
            mFlutterFragment = fragment
            mFlutterView = view
        }

        setContent {
            TrebleKitTheme {
                ActivityMain(flutter = mFlutterView)
            }
        }
    }
}

@Composable
fun ActivityMain(flutter: View?) {
    val tabs = arrayListOf(
        NavigationItem(
            label = "主页",
            icon = Icons.TwoTone.Home,
        ),
        NavigationItem(
            label = "feOS",
            icon = Icons.TwoTone.Memory,
        ),
        NavigationItem(
            label = "EKit",
            icon = Icons.TwoTone.Home,
        ),
        NavigationItem(
            label = "EbKit",
            icon = Icons.TwoTone.Home,
        ),
    )
    LiquidGlassScaffold(
        tabs = tabs,
    ) { page, inner ->
        when (page) {
            0 -> HomePage(inner = inner)
            1 -> FEOSPage(inner = inner, flutter = flutter)
            2 -> EKitPage(inner = inner)
            3 -> EbKitPage(inner = inner)
            else -> UnknownPage(inner = inner)
        }
    }
}

@Preview
@Composable
private fun ActivityMainPreview() {
    TrebleKitTheme {
        ActivityMain(flutter = null)
    }
}


@Composable
fun HomePage(modifier: Modifier = Modifier, inner: PaddingValues) {

}

@Composable
fun FEOSPage(modifier: Modifier = Modifier, inner: PaddingValues, flutter: View?) {
    OutlinedCard(
        modifier = modifier
            .padding(paddingValues = inner)
            .padding(all = 16.dp),
    ) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                flutter ?: View(context)
            },
        )
    }
}

@Composable
fun EKitPage(modifier: Modifier = Modifier, inner: PaddingValues) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            modifier = Modifier.padding(paddingValues = inner),
            text = "EKitPage",
        )
    }
}

@Composable
fun EbKitPage(modifier: Modifier = Modifier, inner: PaddingValues) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            modifier = Modifier.padding(paddingValues = inner),
            text = "EbKitPage",
        )
    }
}

@Composable
fun UnknownPage(modifier: Modifier = Modifier, inner: PaddingValues) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            modifier = Modifier.padding(paddingValues = inner),
            text = "unknown page",
        )
    }
}

