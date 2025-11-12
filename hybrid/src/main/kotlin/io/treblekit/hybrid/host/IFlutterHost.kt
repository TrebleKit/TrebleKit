package io.treblekit.hybrid.host

import io.flutter.embedding.android.FlutterFragment

internal interface IFlutterHost {
    val getFlutterFragment: FlutterFragment?
}