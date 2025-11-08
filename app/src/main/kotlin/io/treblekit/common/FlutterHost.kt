package io.treblekit.common

import androidx.fragment.app.Fragment

interface FlutterHost {
    val getFlutterFragment: Fragment?
}