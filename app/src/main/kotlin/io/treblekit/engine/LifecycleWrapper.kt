package io.treblekit.engine

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

/**
 * 生命周期包装器
 */
interface LifecycleWrapper : LifecycleOwner, DefaultLifecycleObserver