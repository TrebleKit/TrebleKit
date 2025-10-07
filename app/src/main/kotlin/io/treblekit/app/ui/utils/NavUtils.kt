package io.treblekit.app.ui.utils

import androidx.compose.foundation.pager.PagerState
import io.treblekit.app.ui.navigation.appDestination

fun <T : Any> PagerState?.isCurrentPagerDestination(route: T): Boolean {
    return appDestination.indexOfFirst { index ->
        return@indexOfFirst index.route == route
    }.let { current ->
        return@let this@isCurrentPagerDestination?.currentPage == current
    }
}

suspend fun <T : Any> PagerState?.navigateToPagerRoute(route: T) {
    return this@navigateToPagerRoute?.animateScrollToPage(
        page = appDestination.indexOfFirst { index ->
            return@indexOfFirst index.route == route
        },
    ) ?: Unit
}