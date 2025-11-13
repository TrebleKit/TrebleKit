package io.treblekit.engine

import io.treblekit.plugin.IExecutor
import org.koin.core.component.KoinComponent

/**
 * 引擎包装器
 */
interface IEngine : IExecutor, KoinComponent {

    /** 引擎初始化 */
    fun onCreateEngine()

    /** 引擎销毁 */
    fun onDestroyEngine()
}