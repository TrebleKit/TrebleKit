package io.treblekit.engine

/**
 * 方法调用结果回调.
 */
interface EcosedResult {

    /**
     * 处理成功结果.
     * @param result 处理成功结果,注意可能为空.
     */
    fun success(result: Any?)

    /**
     * 处理错误结果.
     * @param errorCode 错误代码.
     * @param errorMessage 错误消息,注意可能为空.
     * @param errorDetails 详细信息,注意可能为空.
     */
    fun error(
        errorCode: String,
        errorMessage: String?,
        errorDetails: Any?,
    ): Nothing

    /**
     * 处理对未实现方法的调用.
     */
    fun notImplemented()
}