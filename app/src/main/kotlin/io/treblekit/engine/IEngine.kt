package io.treblekit.engine

/**
 * 引擎包装器
 */
interface IEngine {

    /** 引擎初始化 */
    fun onCreateEngine()

    /** 引擎销毁 */
    fun onDestroyEngine()
}