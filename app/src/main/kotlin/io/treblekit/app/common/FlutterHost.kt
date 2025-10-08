package io.treblekit.app.common

import io.flutter.embedding.android.FlutterFragment

interface FlutterHost {
    val getFlutterFragment: FlutterFragment?
}