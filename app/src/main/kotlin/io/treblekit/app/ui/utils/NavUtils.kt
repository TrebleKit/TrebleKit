package io.treblekit.app.ui.utils

import androidx.compose.foundation.pager.PagerState
import io.treblekit.app.ui.navigation.appDestination

fun <T : Any> PagerState?.isCurrentDestination(route: T): Boolean {
    return appDestination.indexOfFirst { index ->
        return@indexOfFirst index.route == route
    }.let { current ->
        return@let this@isCurrentDestination?.currentPage == current
    }
}

suspend fun <T : Any> PagerState?.navigateToRoute(route: T) {
    return this@navigateToRoute?.animateScrollToPage(
        page = appDestination.indexOfFirst { index ->
            return@indexOfFirst index.route == route
        },
    ) ?: Unit
}