package io.treblekit.common

import io.flutter.embedding.android.FlutterFragment

interface FlutterHost {
    val getFlutterFragment: FlutterFragment?
}