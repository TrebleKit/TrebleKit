package io.treblekit.engine

import android.os.Bundle

/**
 * 用于调用方法的接口.
 */
interface EcosedMethodCall {

    /**
     * 要调用的方法名.
     */
    val method: String?

    /**
     * 要传入的参数.
     */
    val bundle: Bundle?
}