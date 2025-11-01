//package io.treblekit.engine
//
//import android.app.Activity
//import android.content.ComponentName
//import android.content.Context
//import android.content.Context.BIND_AUTO_CREATE
//import android.content.ContextWrapper
//import android.content.Intent
//import android.content.ServiceConnection
//import android.os.Bundle
//import android.os.Handler
//import android.os.IBinder
//import android.os.Looper
//import android.util.Log
//import android.view.MotionEvent
//import android.view.View
//import android.widget.Toast
//import androidx.appcompat.app.ActionBarDrawerToggle
//import androidx.appcompat.app.AlertDialog
//import androidx.appcompat.app.AppCompatCallback
//import androidx.appcompat.app.AppCompatDelegate
//import androidx.appcompat.view.ActionMode
//import androidx.appcompat.widget.Toolbar
//import androidx.lifecycle.DefaultLifecycleObserver
//import androidx.lifecycle.Lifecycle
//import androidx.lifecycle.LifecycleOwner
//import com.blankj.utilcode.util.AppUtils
//
//class Engine {
//
//    /** 插件绑定器. */
//    private var mBinding: PluginBinding? = null
//
//    /** 插件列表. */
//    private var mPluginList: ArrayList<EcosedPlugin>? = null
//
//
//    /** Activity */
//    private var mActivity: Activity? = null
//
//    /** 生命周期 */
//    private var mLifecycle: Lifecycle? = null
//
//
//    /** 供引擎使用的基本调试布尔值 */
//    private val mBaseDebug: Boolean = AppUtils.isAppDebug()
//
//    /** 全局调试布尔值 */
//    private var mFullDebug: Boolean = false
//
//    /** 此服务意图 */
//    private lateinit var mEcosedServicesIntent: Intent
//
//    /** 服务AIDL接口 */
//    private var mAIDL: IFeOSdk? = null
//
//    /** 服务绑定状态 */
//    private var mIsBind: Boolean = false
//
//
//    /**
//     * Flutter插件代理
//     */
//    private interface FlutterPluginProxy {
//
//        /** 注册Activity引用 */
//        fun onCreateActivity(activity: Activity)
//
//        /** 注销Activity引用 */
//        fun onDestroyActivity()
//
//        /** 注册生命周期监听器 */
//        fun onCreateLifecycle(lifecycle: Lifecycle)
//
//        /** 注销生命周期监听器释放资源避免内存泄露 */
//        fun onDestroyLifecycle()
//
//        fun onActivityResult(
//            requestCode: Int,
//            resultCode: Int,
//            data: Intent?,
//        ): Boolean
//
//        fun onRequestPermissionsResult(
//            requestCode: Int,
//            permissions: Array<out String>,
//            grantResults: IntArray,
//        ): Boolean
//
//        /** 引擎初始化 */
//        fun onCreateEngine(context: Context)
//
//        /** 引擎销毁 */
//        fun onDestroyEngine()
//
//        /** 方法调用 */
//        fun onMethodCall(
//            call: MethodCallProxy,
//            result: ResultProxy,
//        )
//    }
//
//    /**
//     * 方法调用代理
//     */
//    private interface MethodCallProxy {
//
//        /** 方法名代理 */
//        val methodProxy: String
//
//        /** 传入参数代理 */
//        val bundleProxy: Bundle
//    }
//
//    /**
//     * 返回内容代理
//     */
//    private interface ResultProxy {
//
//        /**
//         * 处理成功结果.
//         * @param resultProxy 处理成功结果,注意可能为空.
//         */
//        fun success(resultProxy: Any?)
//
//        /**
//         * 处理错误结果.
//         * @param errorCodeProxy 错误代码.
//         * @param errorMessageProxy 错误消息,注意可能为空.
//         * @param errorDetailsProxy 详细信息,注意可能为空.
//         */
//        fun error(
//            errorCodeProxy: String,
//            errorMessageProxy: String?,
//            errorDetailsProxy: Any?,
//        )
//
//        /**
//         * 处理对未实现方法的调用.
//         */
//        fun notImplemented()
//    }
//
//    /**
//     * 用于调用方法的接口.
//     */
//    private interface EcosedMethodCall {
//
//        /**
//         * 要调用的方法名.
//         */
//        val method: String?
//
//        /**
//         * 要传入的参数.
//         */
//        val bundle: Bundle?
//    }
//
//    /**
//     * 方法调用结果回调.
//     */
//    private interface EcosedResult {
//
//        /**
//         * 处理成功结果.
//         * @param result 处理成功结果,注意可能为空.
//         */
//        fun success(result: Any?)
//
//        /**
//         * 处理错误结果.
//         * @param errorCode 错误代码.
//         * @param errorMessage 错误消息,注意可能为空.
//         * @param errorDetails 详细信息,注意可能为空.
//         */
//        fun error(
//            errorCode: String,
//            errorMessage: String?,
//            errorDetails: Any?,
//        ): Nothing
//
//        /**
//         * 处理对未实现方法的调用.
//         */
//        fun notImplemented()
//    }
//
//    /**
//     * 引擎包装器
//     */
//    private interface EngineWrapper : FlutterPluginProxy {
//
//        /**
//         * 执行方法
//         * @param channel 插件通道
//         * @param method 插件方法
//         * @param bundle 传值
//         * @return 执行插件方法返回值
//         */
//        fun <T> execMethodCall(
//            channel: String,
//            method: String,
//            bundle: Bundle?,
//        ): T?
//    }
//
//    /**
//     * 回调
//     */
//    private interface InvokeWrapper {
//
//        /** 在服务绑定成功时回调 */
//        fun onEcosedConnected()
//
//        /** 在服务解绑或意外断开链接时回调 */
//        fun onEcosedDisconnected()
//
//        /** 在服务端服务未启动时绑定服务时回调 */
//        fun onEcosedDead()
//
//        /** 在未绑定服务状态下调用API时回调 */
//        fun onEcosedUnbind()
//    }
//
//    /**
//     * 生命周期包装器
//     */
//    private interface LifecycleWrapper : LifecycleOwner, DefaultLifecycleObserver
//
//    /**
//     * 服务链接包装器
//     */
//    private interface ConnectWrapper : ServiceConnection
//
//
//    /**
//     * AppCompat包装器
//     * 方法回调和操作栏抽屉状态切换
//     */
//    private interface AppCompatWrapper : AppCompatCallback, ActionBarDrawerToggle.DelegateProvider
//
//    /**
//     * 服务插件包装器
//     */
//    private interface DelegateWrapper : ConnectWrapper, AppCompatWrapper, LifecycleWrapper {
//
//        /**
//         * 获取Binder
//         */
//        fun getBinder(intent: Intent): IBinder
//
//        /**
//         * 附加代理基本上下文
//         */
//        fun attachDelegateBaseContext()
//    }
//
//
//    private interface SdkView {
//        fun getView(): View
//    }
//
//    private abstract class SdkViewFactory {
//
//        abstract fun create(): SdkView
//    }
//
//
//    /**
//     * 基本插件
//     */
//    private abstract class EcosedPlugin : ContextWrapper(null) {
//
//        /** 插件通道 */
//        private lateinit var mPluginChannel: PluginChannel
//
//        /** 引擎 */
//        private lateinit var mEngine: EngineWrapper
//
//        /** 是否调试模式 */
//        private var mDebug: Boolean = false
//
//        /**
//         * 附加基本上下文
//         */
//        override fun attachBaseContext(base: Context?) {
//            super.attachBaseContext(base)
//        }
//
//        /**
//         * 插件添加时执行
//         */
//        open fun onEcosedAdded(binding: PluginBinding) {
//            // 初始化插件通道
//            this@EcosedPlugin.mPluginChannel = PluginChannel(
//                binding = binding,
//                channel = this@EcosedPlugin.channel,
//            )
//            // 插件附加基本上下文
//            this@EcosedPlugin.attachBaseContext(
//                base = this@EcosedPlugin.mPluginChannel.getContext()
//            )
//            // 引擎
//            this@EcosedPlugin.mEngine = this@EcosedPlugin.mPluginChannel.getEngine()
//            // 获取是否调试模式
//            this@EcosedPlugin.mDebug = this@EcosedPlugin.mPluginChannel.isDebug()
//            // 设置调用
//            this@EcosedPlugin.mPluginChannel.setMethodCallHandler(
//                handler = this@EcosedPlugin
//            )
//        }
//
//        /** 获取插件通道 */
//        val getPluginChannel: PluginChannel
//            get() = this@EcosedPlugin.mPluginChannel
//
//        /** 需要子类重写的插件标题 */
//        abstract val title: String
//
//        /** 需要子类重写的通道名称 */
//        abstract val channel: String
//
//        /** 需要子类重写的插件作者 */
//        abstract val author: String
//
//        /** 需要子类重写的插件描述 */
//        abstract val description: String
//
//        /** 供子类使用的判断调试模式的接口 */
//        protected val isDebug: Boolean
//            get() = this@EcosedPlugin.mDebug
//
//        /**
//         * 执行方法
//         * @param channel 插件通道
//         * @param method 插件方法
//         * @param bundle 传值
//         * @return 执行插件方法返回值
//         */
//        open fun <T> execPluginMethod(
//            channel: String,
//            method: String,
//            bundle: Bundle?,
//        ): T? = this@EcosedPlugin.mEngine.execMethodCall(
//            channel = channel,
//            method = method,
//            bundle = bundle,
//        )
//
//        /**
//         * 插件调用方法
//         */
//        open fun onEcosedMethodCall(
//            call: EcosedMethodCall,
//            result: EcosedResult,
//        ) = Unit
//    }
//
//    /**
//     * 插件绑定器
//     */
//    private class PluginBinding(
//        debug: Boolean,
//        context: Context,
//        engine: EngineWrapper,
//    ) {
//
//        /** 是否调试模式. */
//        private val mDebug: Boolean = debug
//
//        /** 应用程序全局上下文. */
//        private val mContext: Context = context
//
//        /** 引擎 */
//        private val mEngine: EngineWrapper = engine
//
//        /**
//         * 是否调试模式.
//         * @return Boolean.
//         */
//        fun isDebug(): Boolean = this@PluginBinding.mDebug
//
//        /**
//         * 获取上下文.
//         * @return Context.
//         */
//        fun getContext(): Context = this@PluginBinding.mContext
//
//        /**
//         * 获取引擎
//         * @return EngineWrapper.
//         */
//        fun getEngine(): EngineWrapper = this@PluginBinding.mEngine
//    }
//
//    /**
//     * 插件通信通道
//     */
//    private class PluginChannel(
//        binding: PluginBinding,
//        channel: String,
//    ) {
//
//        /** 插件绑定器. */
//        private var mBinding: PluginBinding = binding
//
//        /** 插件通道. */
//        private var mChannel: String = channel
//
//        /** 方法调用处理接口. */
//        private var mPlugin: EcosedPlugin? = null
//
//        /** 方法名. */
//        private var mMethod: String? = null
//
//        /** 参数Bundle. */
//        private var mBundle: Bundle? = null
//
//        /** 返回结果. */
//        private var mResult: Any? = null
//
//        /**
//         * 设置方法调用.
//         * @param handler 执行方法时调用EcosedMethodCallHandler.
//         */
//        fun setMethodCallHandler(handler: EcosedPlugin) {
//            this@PluginChannel.mPlugin = handler
//        }
//
//        /**
//         * 获取上下文.
//         * @return Context.
//         */
//        fun getContext(): Context = this@PluginChannel.mBinding.getContext()
//
//        /**
//         * 是否调试模式.
//         * @return Boolean.
//         */
//        fun isDebug(): Boolean = this@PluginChannel.mBinding.isDebug()
//
//        /**
//         * 获取通道.
//         * @return 通道名称.
//         */
//        fun getChannel(): String = this@PluginChannel.mChannel
//
//        /**
//         * 获取引擎.
//         * @return 引擎.
//         */
//        fun getEngine(): EngineWrapper = this@PluginChannel.mBinding.getEngine()
//
//        /**
//         * 执行方法回调.
//         * @param name 通道名称.
//         * @param method 方法名称.
//         * @return 方法执行后的返回值.
//         */
//        @Suppress("UNCHECKED_CAST")
//        fun <T> execMethodCall(
//            name: String,
//            method: String?,
//            bundle: Bundle?,
//        ): T? {
//            this@PluginChannel.mMethod = method
//            this@PluginChannel.mBundle = bundle
//            if (name == this@PluginChannel.mChannel) {
//                this@PluginChannel.mPlugin?.onEcosedMethodCall(
//                    call = this@PluginChannel.call,
//                    result = this@PluginChannel.result,
//                )
//            }
//            return this@PluginChannel.mResult as T?
//        }
//
//        /** 用于调用方法的接口. */
//        private val call: EcosedMethodCall = object : EcosedMethodCall {
//
//            /**
//             * 要调用的方法名.
//             */
//            override val method: String?
//                get() = this@PluginChannel.mMethod
//
//            /**
//             * 要传入的参数.
//             */
//            override val bundle: Bundle?
//                get() = this@PluginChannel.mBundle
//        }
//
//        /** 方法调用结果回调. */
//        private val result: EcosedResult = object : EcosedResult {
//
//            /**
//             * 处理成功结果.
//             */
//            override fun success(result: Any?) {
//                this@PluginChannel.mResult = result
//            }
//
//            /**
//             * 处理错误结果.
//             */
//            override fun error(
//                errorCode: String,
//                errorMessage: String?,
//                errorDetails: Any?,
//            ): Nothing = error(
//                message = "错误代码:$errorCode\n错误消息:$errorMessage\n详细信息:$errorDetails"
//            )
//
//            /**
//             * 处理对未实现方法的调用.
//             */
//            override fun notImplemented() {
//                this@PluginChannel.mResult = null
//            }
//        }
//
//    }
//
//
//    /** 引擎桥接 */
//    private val mEngineBridge: EcosedPlugin = object : EcosedPlugin(), FlutterPluginProxy {
//
//        /** 插件标题 */
//        override val title: String
//            get() = "EngineBridge"
//
//        /** 插件通道 */
//        override val channel: String
//            get() = EcosedChannel.BRIDGE_CHANNEL_NAME
//
//        /** 插件作者 */
//        override val author: String
//            get() = EcosedResources.DEFAULT_AUTHOR
//
//        /** 插件描述 */
//        override val description: String
//            get() = "FlutterEngine与EcosedEngine通信的的桥梁"
//
//        override fun onCreateActivity(activity: Activity) = engineScope {
//            return@engineScope this@engineScope.onCreateActivity(
//                activity = activity,
//            )
//        }
//
//        override fun onDestroyActivity() = engineScope {
//            return@engineScope this@engineScope.onDestroyActivity()
//        }
//
//        override fun onCreateLifecycle(lifecycle: Lifecycle) = engineScope {
//            return@engineScope this@engineScope.onCreateLifecycle(
//                lifecycle = lifecycle,
//            )
//        }
//
//        override fun onDestroyLifecycle() = engineScope {
//            return@engineScope this@engineScope.onDestroyLifecycle()
//        }
//
//        override fun onActivityResult(
//            requestCode: Int,
//            resultCode: Int,
//            data: Intent?,
//        ): Boolean = engineScope {
//            return@engineScope this@engineScope.onActivityResult(
//                requestCode = requestCode,
//                resultCode = resultCode,
//                data = data,
//            )
//        }
//
//        override fun onRequestPermissionsResult(
//            requestCode: Int,
//            permissions: Array<out String>,
//            grantResults: IntArray,
//        ): Boolean = engineScope {
//            return@engineScope this@engineScope.onRequestPermissionsResult(
//                requestCode = requestCode,
//                permissions = permissions,
//                grantResults = grantResults,
//            )
//        }
//
//        override fun onCreateEngine(context: Context) = engineScope {
//            return@engineScope this@engineScope.onCreateEngine(context = context)
//        }
//
//        override fun onDestroyEngine() = engineScope {
//            return@engineScope this@engineScope.onDestroyEngine()
//        }
//
//        override fun onMethodCall(call: MethodCallProxy, result: ResultProxy) = engineScope {
//            return@engineScope this@engineScope.onMethodCall(call = call, result = result)
//        }
//    }
//
//
//    /** 引擎 */
//    private val mEcosedEngine: EcosedPlugin = object : EcosedPlugin(), EngineWrapper {
//
//        /** 插件标题 */
//        override val title: String
//            get() = "EcosedEngine"
//
//        /** 插件通道 */
//        override val channel: String
//            get() = EcosedChannel.ENGINE_CHANNEL_NAME
//
//        /** 插件作者 */
//        override val author: String
//            get() = EcosedResources.DEFAULT_AUTHOR
//
//        /** 插件描述 */
//        override val description: String
//            get() = "Ecosed Engine"
//
//        override fun onCreateActivity(activity: Activity) {
//            this@EbKitCore.mActivity = activity
//        }
//
//        override fun onDestroyActivity() {
//            this@EbKitCore.mActivity = null
//        }
//
//        override fun onCreateLifecycle(lifecycle: Lifecycle) = lifecycleScope {
//            this@EbKitCore.mLifecycle = lifecycle
//            this@lifecycleScope.lifecycle.addObserver(
//                observer = this@lifecycleScope,
//            )
//        }
//
//        override fun onDestroyLifecycle(): Unit = lifecycleScope {
//            this@lifecycleScope.lifecycle.removeObserver(this@lifecycleScope)
//            this@EbKitCore.mLifecycle = null
//        }
//
//        override fun onActivityResult(
//            requestCode: Int,
//            resultCode: Int,
//            data: Intent?,
//        ): Boolean {
//
//            return true
//        }
//
//        override fun onRequestPermissionsResult(
//            requestCode: Int,
//            permissions: Array<out String>,
//            grantResults: IntArray,
//        ): Boolean {
//
//            return true
//        }
//
//        /**
//         * 引擎初始化时执行
//         */
//        override fun onEcosedAdded(binding: PluginBinding): Unit = run {
//            super.onEcosedAdded(binding)
//            // 设置来自插件的全局调试布尔值
//            this@EbKitCore.mFullDebug = this@run.isDebug
//        }
//
//        override fun onEcosedMethodCall(call: EcosedMethodCall, result: EcosedResult) {
//            super.onEcosedMethodCall(call, result)
//            when (call.method) {
//                EcosedMethod.OPEN_DIALOG_METHOD -> result.success(
//                    result = execPluginMethod<Boolean>(
//                        channel = EcosedChannel.INVOKE_CHANNEL_NAME,
//                        method = EcosedMethod.OPEN_DIALOG_METHOD,
//                        bundle = Bundle()
//                    )
//                )
//
//                EcosedMethod.CLOSE_DIALOG_METHOD -> result.success(
//                    result = execPluginMethod<Boolean>(
//                        channel = EcosedChannel.INVOKE_CHANNEL_NAME,
//                        method = EcosedMethod.CLOSE_DIALOG_METHOD,
//                        bundle = Bundle()
//                    )
//                )
//
//                else -> result.notImplemented()
//            }
//        }
//
//        /**
//         * 引擎初始化.
//         * @param context 上下文 - 此上下文来自FlutterPlugin的ApplicationContext
//         */
//        override fun onCreateEngine(context: Context) {
//            when {
//                this@EbKitCore.mPluginList.isNull or this@EbKitCore.mBinding.isNull -> pluginScope(
//                    debug = this@EbKitCore.mBaseDebug,
//                    context = context,
//                ) { plugins, binding ->
//                    // 初始化插件列表.
//                    this@EbKitCore.mPluginList = arrayListOf()
//                    // 添加所有插件.
//                    plugins.forEach { plugin ->
//                        plugin.apply {
//                            try {
//                                this@apply.onEcosedAdded(binding = binding)
//                                if (this@EbKitCore.mBaseDebug) Log.d(
//                                    TAG,
//                                    "插件${this@apply.javaClass.name}已加载",
//                                )
//                            } catch (exception: Exception) {
//                                if (this@EbKitCore.mBaseDebug) Log.e(
//                                    TAG,
//                                    "插件${this@apply.javaClass.name}添加失败!",
//                                    exception,
//                                )
//                            }
//                        }.run {
//                            this@EbKitCore.mPluginList?.add(
//                                element = this@run
//                            )
//                            if (this@EbKitCore.mBaseDebug) Log.d(
//                                TAG,
//                                "插件${this@run.javaClass.name}已添加到插件列表",
//                            )
//                        }
//                    }
//                }
//
//                else -> if (this@EbKitCore.mBaseDebug) Log.e(
//                    TAG, "请勿重复执行onCreateEngine!"
//                ) else Unit
//            }
//        }
//
//        /**
//         * 销毁引擎释放资源.
//         */
//        override fun onDestroyEngine() {
//            when {
//                this@EbKitCore.mPluginList.isNotNull or this@EbKitCore.mBinding.isNotNull -> {
//                    // 清空插件列表
//                    this@EbKitCore.mPluginList = null
//                }
//
//                else -> if (this@EbKitCore.mBaseDebug) Log.e(
//                    TAG,
//                    "请勿重复执行onDestroyEngine!",
//                ) else Unit
//            }
//        }
//
//        /**
//         * 方法调用
//         * 此方法通过Flutter插件代理类[FlutterPluginProxy]实现
//         * 此方法等价与MethodCallHandler的onMethodCall方法
//         * 但参数传递是依赖Bundle进行的
//         */
//        override fun onMethodCall(
//            call: MethodCallProxy,
//            result: ResultProxy,
//        ) {
//            try {
//                // 执行代码并获取执行后的返回值
//                execMethodCall<Any>(
//                    channel = call.bundleProxy.getString(
//                        "channel",
//                        EcosedChannel.ENGINE_CHANNEL_NAME,
//                    ),
//                    method = call.methodProxy,
//                    bundle = call.bundleProxy,
//                ).apply {
//                    // 判断是否为空并提交数据
//                    if (this@apply.isNotNull) result.success(
//                        resultProxy = this@apply
//                    ) else result.notImplemented()
//                }
//            } catch (e: Exception) {
//                // 抛出异常
//                result.error(
//                    errorCodeProxy = TAG,
//                    errorMessageProxy = "engine: onMethodCall",
//                    errorDetailsProxy = Log.getStackTraceString(e),
//                )
//            }
//        }
//
//        /**
//         * 调用插件代码的方法.
//         * @param channel 要调用的插件的通道.
//         * @param method 要调用的插件中的方法.
//         * @param bundle 通过Bundle传递参数.
//         * @return 返回方法执行后的返回值,类型为Any?.
//         */
//        override fun <T> execMethodCall(
//            channel: String,
//            method: String,
//            bundle: Bundle?,
//        ): T? {
//            var result: T? = null
//            try {
//                this@EbKitCore.mPluginList?.forEach { plugin ->
//                    plugin.getPluginChannel.let { pluginChannel ->
//                        if (pluginChannel.getChannel() == channel) {
//                            result = pluginChannel.execMethodCall<T>(
//                                name = channel,
//                                method = method,
//                                bundle = bundle,
//                            )
//                            if (this@EbKitCore.mBaseDebug) Log.d(
//                                TAG,
//                                "插件代码调用成功!\n" + "通道名称:${channel}.\n" + "方法名称:${method}.\n" + "返回结果:${result}.",
//                            )
//                        }
//                    }
//                }
//            } catch (exception: Exception) {
//                if (this@EbKitCore.mBaseDebug) {
//                    Log.e(
//                        TAG,
//                        "插件代码调用失败!",
//                        exception,
//                    )
//                }
//            }
//            return result
//        }
//    }
//
//    /** 负责与服务通信的客户端 */
//    private val mServiceInvoke: EcosedPlugin = object : EcosedPlugin(), InvokeWrapper {
//
//        /** 插件标题 */
//        override val title: String
//            get() = "ServiceInvoke"
//
//        /** 插件通道 */
//        override val channel: String
//            get() = EcosedChannel.INVOKE_CHANNEL_NAME
//
//        /** 插件作者 */
//        override val author: String
//            get() = EcosedResources.DEFAULT_AUTHOR
//
//        /** 插件描述 */
//        override val description: String
//            get() = "负责与服务通信的服务调用"
//
//        /**
//         * 插件添加时执行
//         */
//        override fun onEcosedAdded(binding: PluginBinding) = run {
//            super.onEcosedAdded(binding)
//            this@EbKitCore.mEcosedServicesIntent = Intent(
//                this@run,
//                MainServices().javaClass,
//            )
//            this@EbKitCore.mEcosedServicesIntent.action = EcosedManifest.ACTION
//
//            startService(this@EbKitCore.mEcosedServicesIntent)
//            bindEcosed(this@run)
//
//            Toast.makeText(this@run, "client", Toast.LENGTH_SHORT).show()
//        }
//
//        /**
//         * 插件方法调用
//         */
//        override fun onEcosedMethodCall(call: EcosedMethodCall, result: EcosedResult) {
//            super.onEcosedMethodCall(call, result)
//            when (call.method) {
//                EcosedMethod.OPEN_DIALOG_METHOD -> result.success(result = invokeMethod {
//                    openDialog()
//                })
//
//                EcosedMethod.CLOSE_DIALOG_METHOD -> result.success(result = invokeMethod {
//                    closeDialog()
//                })
//
//                else -> result.notImplemented()
//            }
//        }
//
//        /**
//         * 在服务绑定成功时回调
//         */
//        override fun onEcosedConnected() {
//            Toast.makeText(this, "onEcosedConnected", Toast.LENGTH_SHORT).show()
//        }
//
//        /**
//         * 在服务解绑或意外断开链接时回调
//         */
//        override fun onEcosedDisconnected() {
//            Toast.makeText(this, "onEcosedDisconnected", Toast.LENGTH_SHORT).show()
//        }
//
//        /**
//         * 在服务端服务未启动时绑定服务时回调
//         */
//        override fun onEcosedDead() {
//            Toast.makeText(this, "onEcosedDead", Toast.LENGTH_SHORT).show()
//        }
//
//        /**
//         * 在未绑定服务状态下调用API时回调
//         */
//        override fun onEcosedUnbind() {
//            Toast.makeText(this, "onEcosedUnbind", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    /** 服务相当于整个服务类部分无法在大类中实现的方法在此实现并调用 */
//    private val mServiceDelegate: EcosedPlugin = object : EcosedPlugin(), DelegateWrapper {
//
//        /** 插件标题 */
//        override val title: String
//            get() = "ServiceDelegate"
//
//        /** 插件通道 */
//        override val channel: String
//            get() = EcosedChannel.DELEGATE_CHANNEL_NAME
//
//        /** 插件作者 */
//        override val author: String
//            get() = EcosedResources.DEFAULT_AUTHOR
//
//        /** 插件描述 */
//        override val description: String
//            get() = "服务功能代理, 无实际插件方法实现."
//
//        override fun attachBaseContext(base: Context?): Unit = base?.run {
//            super.attachBaseContext(base)
//            this@EbKitCore.mAppCompatDelegateBaseContext = this@run
//        } ?: Unit
//
//        /**
//         * 获取Binder
//         * @param intent 意图
//         * @return IBinder
//         */
//        override fun getBinder(intent: Intent): IBinder {
//            return object : IFeOSdk.Stub() {}
//        }
//
//        override fun attachDelegateBaseContext() {
//            this@EbKitCore.mAppCompatDelegate.attachBaseContext2(
//                this@EbKitCore.mAppCompatDelegateBaseContext
//            )
//        }
//
//        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
//            when (name?.className) {
////                UserService().javaClass.name -> {
////                    if (service.isNotNull and (service?.pingBinder() == true)) {
////                        this@FeOSdk.mIUserService =
////                            IUserService.Stub.asInterface(service)
////                    }
////                    when {
////                        this@FeOSdk.mIUserService.isNotNull -> {
////                            Toast.makeText(this, "mIUserService", Toast.LENGTH_SHORT).show()
////                        }
////
////                        else -> if (this@FeOSdk.mFullDebug) Log.e(
////                            TAG, "UserService接口获取失败 - onServiceConnected"
////                        )
////                    }
////                    when {
////                        this@FeOSdk.mFullDebug -> Log.i(
////                            TAG, "服务已连接 - onServiceConnected"
////                        )
////                    }
////                }
//
//                MainServices().javaClass.name -> {
//                    if (service.isNotNull and (service?.pingBinder() == true)) {
//                        this@EbKitCore.mAIDL = IFeOSdk.Stub.asInterface(service)
//                    }
//                    when {
//                        this@EbKitCore.mAIDL.isNotNull -> {
//                            this@EbKitCore.mIsBind = true
//                            invokeScope {
//                                onEcosedConnected()
//                            }
//                        }
//
//                        else -> if (this@EbKitCore.mFullDebug) Log.e(
//                            TAG, "AIDL接口获取失败 - onServiceConnected"
//                        )
//                    }
//                    when {
//                        mFullDebug -> Log.i(
//                            TAG, "服务已连接 - onServiceConnected"
//                        )
//                    }
//                }
//
//                else -> {
//
//                }
//            }
//        }
//
//        override fun onServiceDisconnected(name: ComponentName?) {
//            when (name?.className) {
////                UserService().javaClass.name -> {
////
////                }
//
//                this@EbKitCore.javaClass.name -> {
//                    this@EbKitCore.mIsBind = false
//                    this@EbKitCore.mAIDL = null
//                    unbindService(this)
//                    invokeScope {
//                        onEcosedDisconnected()
//                    }
//                    if (this@EbKitCore.mFullDebug) {
//                        Log.i(TAG, "服务意外断开连接 - onServiceDisconnected")
//                    }
//                }
//
//                else -> {
//
//                }
//            }
//
//        }
//
//        override fun onBindingDied(name: ComponentName?) {
//            super.onBindingDied(name)
//            when (name?.className) {
////                UserService().javaClass.name -> {
////
////                }
//
//                this@EbKitCore.javaClass.name -> {
//
//                }
//
//                else -> {
//
//                }
//            }
//        }
//
//        override fun onNullBinding(name: ComponentName?) {
//            super.onNullBinding(name)
//            when (name?.className) {
////                UserService().javaClass.name -> {
////
////                }
//
//                this@EbKitCore.javaClass.name -> {
//                    if (this@EbKitCore.mFullDebug) {
//                        Log.e(TAG, "Binder为空 - onNullBinding")
//                    }
//                }
//
//                else -> {
//
//                }
//            }
//        }
//
////        override fun onBinderReceived() {
////            Toast.makeText(this, "onBinderReceived", Toast.LENGTH_SHORT).show()
////        }
////
////        override fun onBinderDead() {
////            Toast.makeText(this, "onBinderDead", Toast.LENGTH_SHORT).show()
////        }
////
////        override fun onRequestPermissionResult(requestCode: Int, grantResult: Int) {
////            Toast.makeText(this, "onRequestPermissionResult", Toast.LENGTH_SHORT).show()
////        }
//
//        override fun onSupportActionModeStarted(mode: ActionMode?) {
//
//        }
//
//        override fun onSupportActionModeFinished(mode: ActionMode?) {
//
//        }
//
//        override fun onWindowStartingSupportActionMode(callback: ActionMode.Callback?): ActionMode? {
//            return null
//        }
//
//        override fun getDrawerToggleDelegate(): ActionBarDrawerToggle.Delegate? {
//            return mAppCompatDelegate.drawerToggleDelegate
//        }
//
//        override val lifecycle: Lifecycle
//            get() = mLifecycle ?: error(message = "lifecycle is null!")
//
//        /**
//         * 活动创建时执行
//         */
//        override fun onCreate(owner: LifecycleOwner): Unit = activityScope {
//            super.onCreate(owner)
//            // 初始化
//            init {
//                delegateScope {
//                    // 调用Delegate onCreate函数
//                    onCreate(Bundle())
//                }
//            }
//            // 切换工具栏状态
//            //toggle()
//
//            // 执行Delegate函数
//            if (this@activityScope.isNotAppCompat) delegateScope {
//                onPostCreate(Bundle())
//            }
//        }
//
//        /**
//         * 活动启动时执行
//         */
//        override fun onStart(owner: LifecycleOwner): Unit = activityScope {
//            super.onStart(owner)
//            // 执行Delegate onStart函数
//            if (this@activityScope.isNotAppCompat) delegateScope {
//                onStart()
//            }
//        }
//
//        /**
//         * 活动恢复时执行
//         */
//        override fun onResume(owner: LifecycleOwner): Unit = activityScope {
//            super.onResume(owner)
//            // 执行Delegate onPostResume函数
//            if (this@activityScope.isNotAppCompat) delegateScope {
//                onPostResume()
//            }
//        }
//
//        /**
//         * 活动暂停时执行
//         */
//        override fun onPause(owner: LifecycleOwner): Unit = activityScope {
//            super.onPause(owner)
//        }
//
//        /**
//         * 活动停止时执行
//         */
//        override fun onStop(owner: LifecycleOwner): Unit = activityScope {
//            super.onStop(owner)
//            if (this@activityScope.isNotAppCompat) delegateScope {
//                onStop()
//            }
//        }
//
//        /**
//         * 活动销毁时执行
//         */
//        override fun onDestroy(owner: LifecycleOwner): Unit = activityScope {
//            super.onDestroy(owner)
//            if (this@activityScope.isNotAppCompat) delegateScope {
//                onDestroy()
//            }
//        }
//    }
//
//    /**
//     * 调用方法
//     */
//    private inline fun invokeMethod(block: () -> Unit): Boolean {
//        try {
//            block.invoke()
//            return true
//        } catch (_: Exception) {
//            return false
//        }
//    }
//
//
//    /**
//     * 判断是否支持谷歌基础服务
//     */
//    private fun isSupportGMS(): Boolean = activityScope {
//        return@activityScope if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
//                this@activityScope
//            ) == ConnectionResult.SUCCESS
//        ) true else AppUtils.isAppInstalled(
//            EcosedManifest.GMS_PACKAGE
//        )
//    }
//
//
//    /**
//     * 绑定服务
//     * @param context 上下文
//     */
//    private fun bindEcosed(context: Context) = connectScope {
//        try {
//            if (!mIsBind) {
//                context.bindService(
//                    this@EbKitCore.mEcosedServicesIntent,
//                    this@connectScope,
//                    BIND_AUTO_CREATE,
//                ).let { bind ->
//                    invokeScope {
//                        if (!bind) onEcosedDead()
//                    }
//                }
//            }
//        } catch (e: Exception) {
//            if (this@EbKitCore.mFullDebug) {
//                Log.e(TAG, "bindEcosed", e)
//            }
//        }
//    }
//
//    /**
//     * 解绑服务
//     * @param context 上下文
//     */
//    private fun unbindEcosed(context: Context) = connectScope {
//        try {
//            if (this@EbKitCore.mIsBind) {
//                context.unbindService(
//                    this@connectScope
//                ).run {
//                    this@EbKitCore.mIsBind = false
//                    this@EbKitCore.mAIDL = null
//                    invokeScope {
//                        onEcosedDisconnected()
//                    }
//                    if (this@EbKitCore.mFullDebug) {
//                        Log.i(TAG, "服务已断开连接 - onServiceDisconnected")
//                    }
//                }
//            }
//        } catch (e: Exception) {
//            if (mFullDebug) {
//                Log.e(TAG, "unbindEcosed", e)
//            }
//        }
//    }
//
//    private fun gms(context: Context) {
//        try {
//            val intent = Intent(Intent.ACTION_MAIN)
//            intent.setPackage(EcosedManifest.GMS_PACKAGE)
//            try {
//                context.startActivity(intent)
//            } catch (e: Exception) {
//                Log.w(TAG, "MAIN activity is not DEFAULT. Trying to resolve instead.")
//                intent.setClassName(
//                    EcosedManifest.GMS_PACKAGE,
//                    packageManager.resolveActivity(intent, 0)!!.activityInfo.name
//                )
//                context.startActivity(intent)
//            }
//            Toast.makeText(context, "toast_installed", Toast.LENGTH_LONG).show()
//        } catch (e: Exception) {
//            Log.w(TAG, "Failed launching microG Settings", e)
//            Toast.makeText(context, "toast_not_installed", Toast.LENGTH_LONG).show()
//        }
//
//    }
//
//
//    /**
//     * 框架调用单元
//     * Flutter插件调用框架
//     * @param content Flutter插件代理单元
//     * @return content 返回值
//     */
//    private fun <R> bridgeScope(
//        content: FlutterPluginProxy.() -> R,
//    ): R = content.invoke(
//        mEngineBridge.run {
//            return@run when (this@run) {
//                is FlutterPluginProxy -> this@run
//                else -> error(
//                    message = "引擎桥接未实现插件代理方法"
//                )
//            }
//        },
//    )
//
//    /**
//     * 引擎调用单元
//     * 框架调用引擎
//     * @param content 引擎包装器单元
//     * @return content 返回值
//     */
//    private fun <R> engineScope(
//        content: EngineWrapper.() -> R,
//    ): R = content.invoke(
//        mEcosedEngine.run {
//            return@run when (this@run) {
//                is EngineWrapper -> this@run
//                else -> error(
//                    message = "引擎未实现引擎包装器方法"
//                )
//            }
//        },
//    )
//
//    /**
//     * 生命周期调用单元
//     * 调用生命周期所有者和生命周期观察者
//     * @param content 生命周期包装器
//     * @return content 返回值
//     */
//    private fun <R> lifecycleScope(
//        content: LifecycleWrapper.() -> R
//    ): R = content.invoke(
//        mServiceDelegate.run {
//            return@run when (this@run) {
//                is LifecycleWrapper -> this@run
//                else -> error(
//                    message = "服务代理未实现生命周期包装器方法"
//                )
//            }
//        },
//    )
//
//    /**
//     * 插件调用单元
//     * 插件初始化
//     * @param context 上下文
//     * @param content 插件列表单元, 插件绑定器
//     * @return content 返回值
//     */
//    private fun <R> pluginScope(
//        context: Context,
//        debug: Boolean,
//        content: (ArrayList<EcosedPlugin>, PluginBinding) -> R,
//    ): R = content.invoke(
//        arrayListOf(
//            mEngineBridge,
//            mEcosedEngine,
//            mServiceInvoke,
//            mServiceDelegate,
//        ), PluginBinding(
//            debug = debug,
//            context = context,
//            engine = mEcosedEngine.run {
//                return@run when (this@run) {
//                    is EngineWrapper -> this@run
//                    else -> error(
//                        message = "引擎未实现引擎包装器方法"
//                    )
//                }
//            },
//        )
//    )
//
//    /**
//     * 客户端回调调用单元
//     * 绑定解绑调用客户端回调
//     * @param content 客户端回调单元
//     * @return content 返回值
//     */
//    private fun <R> invokeScope(
//        content: InvokeWrapper.() -> R,
//    ): R = content.invoke(
//        mServiceInvoke.run {
//            return@run when (this@run) {
//                is InvokeWrapper -> this@run
//                else -> error(
//                    message = "服务调用插件未实现客户端包装器方法"
//                )
//            }
//        },
//    )
//
//    /**
//     * 服务调用单元
//     * 服务与服务嗲用
//     * @param content 服务
//     * @return content 返回值
//     */
//    private fun <R> serviceScope(
//        content: DelegateWrapper.() -> R,
//    ): R = content.invoke(
//        mServiceDelegate.run {
//            return@run when (this@run) {
//                is DelegateWrapper -> this@run
//                else -> error(
//                    message = "服务代理未实现服务代理包装器方法"
//                )
//            }
//        },
//    )
//
//    /**
//     * 服务连接器调用单元
//     * 调用服务连接包装器
//     * @param content 服务链接包装器
//     * @return content 返回值
//     */
//    private fun <R> connectScope(
//        content: ConnectWrapper.() -> R,
//    ): R = content.invoke(
//        mServiceDelegate.run {
//            return@run when (this@run) {
//                is ConnectWrapper -> this@run
//                else -> error(
//                    message = "服务代理未实现连接包装器方法"
//                )
//            }
//        },
//    )
//
//    /**
//     * AppCompat方法调用单元
//     * 调用AppCompat包装器方法
//     * @param content AppCompat
//     * @return content 返回值
//     */
//    private fun <R> appCompatScope(
//        content: AppCompatWrapper.() -> R,
//    ): R = content.invoke(
//        mServiceDelegate.run {
//            return@run when (this@run) {
//                is AppCompatWrapper -> this@run
//                else -> error(
//                    message = "服务代理未实现AppCompat包装器方法"
//                )
//            }
//        },
//    )
//
//    /**
//     * Activity上下文调用单元
//     * Activity生命周期观察者通过此调用单元执行基于Activity上下文的代码
//     * @param content 内容
//     * @return content 返回值
//     */
//    private fun <R> activityScope(
//        content: Activity.() -> R,
//    ): R = content.invoke(
//        mActivity ?: error(
//            message = "activity is null"
//        ),
//    )
//
//    /**
//     * 资源
//     */
//    private object EcosedResources {
//
//        /** 开发者 */
//        const val DEFAULT_AUTHOR: String = "wyq0918dev"
//
//        /** 确定按钮文本 */
//        const val POSITIVE_BUTTON_STRING: String = "确定"
//
//    }
//
//    /**
//     * 清单
//     */
//    private object EcosedManifest {
//        /** 服务动作 */
//        const val ACTION: String = "io.freefeos.sdk.action"
//
//        const val WECHAT_PACKAGE: String = "com.tencent.mm"
//
//        /** 谷歌基础服务包名 */
//        const val GMS_PACKAGE: String = "com.google.android.gms"
//
//        const val GMS_CLASS: String = "com.google.android.gms.app.settings.GoogleSettingsLink"
//
//        /** 签名伪装权限 */
//        const val FAKE_PACKAGE_SIGNATURE: String = "android.permission.FAKE_PACKAGE_SIGNATURE"
//    }
//
//    /**
//     * 通道
//     */
//    private object EcosedChannel {
//        /** Flutter插件通道名称 */
//        const val FLUTTER_CHANNEL_NAME: String = "flutter_ecosed"
//
//        /** 引擎桥梁插件 */
//        const val BRIDGE_CHANNEL_NAME: String = "ecosed_bridge"
//
//        /** 引擎 */
//        const val ENGINE_CHANNEL_NAME: String = "ecosed_engine"
//
//        /** 服务调用插件 */
//        const val INVOKE_CHANNEL_NAME: String = "ecosed_invoke"
//
//        /** 服务代理插件 */
//        const val DELEGATE_CHANNEL_NAME: String = "ecosed_delegate"
//    }
//
//    /**
//     * 方法
//     */
//    private object EcosedMethod {
//
//        /** 打开对话框 */
//        const val OPEN_DIALOG_METHOD: String = "openDialog"
//
//        /** 关闭对话框 */
//        const val CLOSE_DIALOG_METHOD: String = "closeDialog"
//    }
//}