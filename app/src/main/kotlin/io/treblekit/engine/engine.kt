package io.treblekit.engine

import android.app.Activity
import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.kongzue.dialogx.dialogs.PopTip
import io.treblekit.BuildConfig
import io.treblekit.aidl.ITrebleKit
import io.treblekit.app.MainService
import io.treblekit.di.PLUGIN_INSERT_NAMED
import org.koin.core.component.inject
import org.koin.core.qualifier.named

fun Application.loadTrebleEngine() {
    Engine().entry(this)
}


class Engine {

    fun entry(context: Context) {
        engineScope {
            onCreateEngine(context)
        }
    }


    /** Activity */
    private var mActivity: Activity? = null

    /** 生命周期 */
    private var mLifecycle: Lifecycle? = null

    /** 全局调试布尔值 */
    private var mFullDebug: Boolean = true

    /** 此服务意图 */
    private lateinit var mEcosedServicesIntent: Intent

    /** 服务AIDL接口 */
    private var mAIDL: ITrebleKit? = null

    /** 服务绑定状态 */
    private var mIsBind: Boolean = false


    /** 引擎 */
    private val mEcosedEngine: EcosedPlugin = object : EcosedPlugin(), EngineWrapper {

        /** 供引擎使用的基本调试布尔值 */
        private val mBaseDebug: Boolean = BuildConfig.DEBUG

        /** 插件绑定器. */
        private var mBinding: PluginBinding? = null

        /** 插件列表. */
        private var mPluginList: ArrayList<EcosedPlugin>? = null

        /** 插件标题 */
        override val title: String
            get() = "EcosedEngine"

        /** 插件通道 */
        override val channel: String
            get() = EcosedChannel.ENGINE_CHANNEL_NAME

        /** 插件描述 */
        override val description: String
            get() = "Ecosed Engine"

        /**
         * 引擎初始化时执行
         */
        override fun onEcosedAdded(binding: PluginBinding): Unit = run {
            super.onEcosedAdded(binding)
            // 设置来自插件的全局调试布尔值
            mFullDebug = this@run.isDebug
        }

        override fun onEcosedMethodCall(call: EcosedMethodCall, result: EcosedResult) {
            super.onEcosedMethodCall(call, result)
            when (call.method) {
                "hello" -> {
                    PopTip.show("hello")
                    result.success(true)
                }

                EcosedMethod.OPEN_DIALOG_METHOD -> result.success(
                    result = execPluginMethod<Boolean>(
                        channel = EcosedChannel.INVOKE_CHANNEL_NAME,
                        method = EcosedMethod.OPEN_DIALOG_METHOD,
                        bundle = Bundle()
                    )
                )

                EcosedMethod.CLOSE_DIALOG_METHOD -> result.success(
                    result = execPluginMethod<Boolean>(
                        channel = EcosedChannel.INVOKE_CHANNEL_NAME,
                        method = EcosedMethod.CLOSE_DIALOG_METHOD,
                        bundle = Bundle()
                    )
                )

                else -> result.notImplemented()
            }
        }

        /**
         * 引擎初始化.
         * @param context 上下文 - 此上下文来自FlutterPlugin的ApplicationContext
         */
        override fun onCreateEngine(context: Context) {
            if (mPluginList.isNull or mBinding.isNull) {
                // 初始化插件列表.
                mPluginList = arrayListOf()
                val bridge: EcosedPlugin by inject<EcosedPlugin>(
                    qualifier = named(name = PLUGIN_INSERT_NAMED),
                )
                val binding = PluginBinding(
                    debug = mBaseDebug,
                    context = context,
                    engine = this,
                )
                // 添加所有插件.
                arrayListOf(
                    bridge,
                    mEcosedEngine,
                    mServiceInvoke,
                    mServiceDelegate,
                ).forEach { plugin ->
                    plugin.apply {
                        try {
                            this@apply.onEcosedAdded(binding = binding)
                            if (mBaseDebug) Log.d(
                                TAG,
                                "插件${this@apply.javaClass.name}已加载",
                            )
                        } catch (exception: Exception) {
                            if (mBaseDebug) Log.e(
                                TAG,
                                "插件${this@apply.javaClass.name}添加失败!",
                                exception,
                            )
                        }
                    }.run {
                        mPluginList?.add(
                            element = this@run
                        )
                        if (mBaseDebug) Log.d(
                            TAG,
                            "插件${this@run.javaClass.name}已添加到插件列表",
                        )
                    }
                }
            } else {
                if (mBaseDebug) {
                    Log.e(
                        TAG, "请勿重复执行onCreateEngine!"
                    )
                }
            }
        }

        /**
         * 销毁引擎释放资源.
         */
        override fun onDestroyEngine() {
            if (mPluginList.isNotNull or mBinding.isNotNull) {
                // 清空插件列表
                mPluginList = null
            } else if (mBaseDebug) {
                Log.e(TAG, "请勿重复执行onDestroyEngine!")
            }
        }

        /**
         * 调用插件代码的方法.
         * @param channel 要调用的插件的通道.
         * @param method 要调用的插件中的方法.
         * @param bundle 通过Bundle传递参数.
         * @return 返回方法执行后的返回值,类型为Any?.
         */
        override fun <T> execMethodCall(
            channel: String,
            method: String,
            bundle: Bundle?,
        ): T? {
            var result: T? = null
            try {
                mPluginList?.forEach { plugin ->
                    plugin.getPluginChannel.let { pluginChannel ->
                        if (pluginChannel.getChannel() == channel) {
                            result = pluginChannel.execMethodCall(
                                name = channel,
                                method = method,
                                bundle = bundle,
                            )
                            if (mBaseDebug) Log.d(
                                TAG,
                                "插件代码调用成功!\n" + "通道名称:${channel}.\n" + "方法名称:${method}.\n" + "返回结果:${result}.",
                            )
                        }
                    }
                }
            } catch (exception: Exception) {
                if (mBaseDebug) {
                    Log.e(
                        TAG,
                        "插件代码调用失败!",
                        exception,
                    )
                }
            }
            return result
        }
    }

    /** 负责与服务通信的客户端 */
    private val mServiceInvoke: EcosedPlugin = object : EcosedPlugin(), InvokeWrapper {

        /** 插件标题 */
        override val title: String
            get() = "ServiceInvoke"

        /** 插件通道 */
        override val channel: String
            get() = EcosedChannel.INVOKE_CHANNEL_NAME

        /** 插件描述 */
        override val description: String
            get() = "负责与服务通信的服务调用"

        /**
         * 插件添加时执行
         */
        override fun onEcosedAdded(binding: PluginBinding) = run {
            super.onEcosedAdded(binding)
            mEcosedServicesIntent = Intent(
                this@run,
                MainService::class.java,
            )
            mEcosedServicesIntent.action = EcosedManifest.ACTION

            startService(mEcosedServicesIntent)
            bindEcosed(this@run)

            Toast.makeText(this@run, "client", Toast.LENGTH_SHORT).show()
        }

        /**
         * 插件方法调用
         */
        override fun onEcosedMethodCall(call: EcosedMethodCall, result: EcosedResult) {
            super.onEcosedMethodCall(call, result)
            when (call.method) {
//                EcosedMethod.OPEN_DIALOG_METHOD -> result.success(result = invokeMethod {
//                    openDialog()
//                })
//
//                EcosedMethod.CLOSE_DIALOG_METHOD -> result.success(result = invokeMethod {
//                    closeDialog()
//                })

                else -> result.notImplemented()
            }
        }

        /**
         * 在服务绑定成功时回调
         */
        override fun onEcosedConnected() {
            Toast.makeText(this, "onEcosedConnected", Toast.LENGTH_SHORT).show()
        }

        /**
         * 在服务解绑或意外断开链接时回调
         */
        override fun onEcosedDisconnected() {
            Toast.makeText(this, "onEcosedDisconnected", Toast.LENGTH_SHORT).show()
        }

        /**
         * 在服务端服务未启动时绑定服务时回调
         */
        override fun onEcosedDead() {
            Toast.makeText(this, "onEcosedDead", Toast.LENGTH_SHORT).show()
        }

        /**
         * 在未绑定服务状态下调用API时回调
         */
        override fun onEcosedUnbind() {
            Toast.makeText(this, "onEcosedUnbind", Toast.LENGTH_SHORT).show()
        }
    }

    /** 服务相当于整个服务类部分无法在大类中实现的方法在此实现并调用 */
    private val mServiceDelegate: EcosedPlugin = object : EcosedPlugin(), DelegateWrapper {

        /** 插件标题 */
        override val title: String
            get() = "ServiceDelegate"

        /** 插件通道 */
        override val channel: String
            get() = EcosedChannel.DELEGATE_CHANNEL_NAME

        /** 插件描述 */
        override val description: String
            get() = "服务功能代理, 无实际插件方法实现."

        /**
         * 获取Binder
         * @param intent 意图
         * @return IBinder
         */
        override fun getBinder(intent: Intent): IBinder {
            return object : ITrebleKit.Stub() {}
        }

        override fun attachDelegateBaseContext() {

        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            when (name?.className) {
//                UserService::class.java.name -> {
//                    if (service.isNotNull and (service?.pingBinder() == true)) {
//                        this@FeOSdk.mIUserService =
//                            IUserService.Stub.asInterface(service)
//                    }
//                    when {
//                        this@FeOSdk.mIUserService.isNotNull -> {
//                            Toast.makeText(this, "mIUserService", Toast.LENGTH_SHORT).show()
//                        }
//
//                        else -> if (this@FeOSdk.mFullDebug) Log.e(
//                            TAG, "UserService接口获取失败 - onServiceConnected"
//                        )
//                    }
//                    when {
//                        this@FeOSdk.mFullDebug -> Log.i(
//                            TAG, "服务已连接 - onServiceConnected"
//                        )
//                    }
//                }

                MainService().javaClass.name -> {
                    if (service.isNotNull and (service?.pingBinder() == true)) {
                        mAIDL = ITrebleKit.Stub.asInterface(service)
                    }
                    if (mAIDL.isNotNull) {
                        mIsBind = true
                        invokeScope {
                            onEcosedConnected()
                        }
                    } else if (mFullDebug) Log.e(
                        TAG, "AIDL接口获取失败 - onServiceConnected"
                    )
                    if (mFullDebug) {
                        Log.i(
                            TAG, "服务已连接 - onServiceConnected"
                        )
                    }

                }

                else -> {

                }
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            when (name?.className) {
//                UserService::class.java.name -> {
//
//                }

                MainService::class.java.name -> {
                    mIsBind = false
                    mAIDL = null
                    unbindService(this)
                    invokeScope {
                        onEcosedDisconnected()
                    }
                    if (mFullDebug) {
                        Log.i(TAG, "服务意外断开连接 - onServiceDisconnected")
                    }
                }

                else -> {

                }
            }

        }

        override fun onBindingDied(name: ComponentName?) {
            super.onBindingDied(name)
            when (name?.className) {
//                UserService::class.java.name -> {
//
//                }

                MainService::class.java.name -> {

                }

                else -> {

                }
            }
        }

        override fun onNullBinding(name: ComponentName?) {
            super.onNullBinding(name)
            when (name?.className) {
//                UserService::class.java.name -> {
//
//                }

                MainService::class.java.name -> {
                    if (mFullDebug) {
                        Log.e(TAG, "Binder为空 - onNullBinding")
                    }
                }

                else -> {

                }
            }
        }

//        override fun onBinderReceived() {
//            Toast.makeText(this, "onBinderReceived", Toast.LENGTH_SHORT).show()
//        }
//
//        override fun onBinderDead() {
//            Toast.makeText(this, "onBinderDead", Toast.LENGTH_SHORT).show()
//        }
//
//        override fun onRequestPermissionResult(requestCode: Int, grantResult: Int) {
//            Toast.makeText(this, "onRequestPermissionResult", Toast.LENGTH_SHORT).show()
//        }


        override val lifecycle: Lifecycle
            get() = mLifecycle ?: error(message = "lifecycle is null!")

        /**
         * 活动创建时执行
         */
        override fun onCreate(owner: LifecycleOwner): Unit = activityScope {
            super.onCreate(owner)
        }

        /**
         * 活动启动时执行
         */
        override fun onStart(owner: LifecycleOwner): Unit = activityScope {
            super.onStart(owner)
        }

        /**
         * 活动恢复时执行
         */
        override fun onResume(owner: LifecycleOwner): Unit = activityScope {
            super.onResume(owner)
        }

        /**
         * 活动暂停时执行
         */
        override fun onPause(owner: LifecycleOwner): Unit = activityScope {
            super.onPause(owner)
        }

        /**
         * 活动停止时执行
         */
        override fun onStop(owner: LifecycleOwner): Unit = activityScope {
            super.onStop(owner)
        }

        /**
         * 活动销毁时执行
         */
        override fun onDestroy(owner: LifecycleOwner): Unit = activityScope {
            super.onDestroy(owner)
        }
    }


    /**
     * 绑定服务
     * @param context 上下文
     */
    private fun bindEcosed(context: Context) = connectScope {
        try {
            if (!mIsBind) {
                context.bindService(
                    mEcosedServicesIntent,
                    this@connectScope,
                    BIND_AUTO_CREATE,
                ).let { bind ->
                    invokeScope {
                        if (!bind) onEcosedDead()
                    }
                }
            }
        } catch (e: Exception) {
            if (mFullDebug) {
                Log.e(TAG, "bindEcosed", e)
            }
        }
    }

    /**
     * 解绑服务
     * @param context 上下文
     */
    private fun unbindEcosed(context: Context) = connectScope {
        try {
            if (mIsBind) {
                context.unbindService(
                    this@connectScope
                ).run {
                    mIsBind = false
                    mAIDL = null
                    invokeScope {
                        onEcosedDisconnected()
                    }
                    if (mFullDebug) {
                        Log.i(TAG, "服务已断开连接 - onServiceDisconnected")
                    }
                }
            }
        } catch (e: Exception) {
            if (mFullDebug) {
                Log.e(TAG, "unbindEcosed", e)
            }
        }
    }

    /**
     * 引擎调用单元
     * 框架调用引擎
     * @param content 引擎包装器单元
     * @return content 返回值
     */
    private fun <R> engineScope(
        content: EngineWrapper.() -> R,
    ): R = content.invoke(
        mEcosedEngine.run {
            return@run when (this@run) {
                is EngineWrapper -> this@run
                else -> error(
                    message = "引擎未实现引擎包装器方法"
                )
            }
        },
    )

    /**
     * 生命周期调用单元
     * 调用生命周期所有者和生命周期观察者
     * @param content 生命周期包装器
     * @return content 返回值
     */
    private fun <R> lifecycleScope(
        content: LifecycleWrapper.() -> R
    ): R = content.invoke(
        mServiceDelegate.run {
            return@run when (this@run) {
                is LifecycleWrapper -> this@run
                else -> error(
                    message = "服务代理未实现生命周期包装器方法"
                )
            }
        },
    )

    /**
     * 客户端回调调用单元
     * 绑定解绑调用客户端回调
     * @param content 客户端回调单元
     * @return content 返回值
     */
    private fun <R> invokeScope(
        content: InvokeWrapper.() -> R,
    ): R = content.invoke(
        mServiceInvoke.run {
            return@run when (this@run) {
                is InvokeWrapper -> this@run
                else -> error(
                    message = "服务调用插件未实现客户端包装器方法"
                )
            }
        },
    )

    /**
     * 服务调用单元
     * 服务与服务嗲用
     * @param content 服务
     * @return content 返回值
     */
    private fun <R> serviceScope(
        content: DelegateWrapper.() -> R,
    ): R = content.invoke(
        mServiceDelegate.run {
            return@run when (this@run) {
                is DelegateWrapper -> this@run
                else -> error(
                    message = "服务代理未实现服务代理包装器方法"
                )
            }
        },
    )

    /**
     * 服务连接器调用单元
     * 调用服务连接包装器
     * @param content 服务链接包装器
     * @return content 返回值
     */
    private fun <R> connectScope(
        content: ConnectWrapper.() -> R,
    ): R = content.invoke(
        mServiceDelegate.run {
            return@run when (this@run) {
                is ConnectWrapper -> this@run
                else -> error(
                    message = "服务代理未实现连接包装器方法"
                )
            }
        },
    )

    /**
     * Activity上下文调用单元
     * Activity生命周期观察者通过此调用单元执行基于Activity上下文的代码
     * @param content 内容
     * @return content 返回值
     */
    private fun <R> activityScope(
        content: Activity.() -> R,
    ): R = content.invoke(
        mActivity ?: error(
            message = "activity is null"
        ),
    )


    /**
     * 构建器(伴生对象)
     */
    companion object {

        /** 用于打印日志的标签 */
        const val TAG: String = "FeOSdk"
    }
}