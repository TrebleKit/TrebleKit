package io.treblekit.engine

import android.content.ComponentName
import android.content.Context
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import io.treblekit.aidl.ITrebleKit
import io.treblekit.app.MainService
import io.treblekit.plugin.PluginBinding
import io.treblekit.plugin.PluginMethodCall
import io.treblekit.plugin.PluginResult
import io.treblekit.plugin.TreblePlugin
import io.treblekit.utils.isNotNull

class Engine {

    /** 全局调试布尔值 */
    private var mFullDebug: Boolean = true

    /** 此服务意图 */
    private lateinit var mEcosedServicesIntent: Intent

    /** 服务AIDL接口 */
    private var mAIDL: ITrebleKit? = null

    /** 服务绑定状态 */
    private var mIsBind: Boolean = false

    /** 负责与服务通信的客户端 */
    private val mServiceInvoke: TreblePlugin = object : TreblePlugin(), InvokeWrapper {

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
            mEcosedServicesIntent.action = "io.treblekit.action"

            startService(mEcosedServicesIntent)
            (mServiceDelegate as ServiceConnection).bindEcosed(this@run)

            Toast.makeText(this@run, "client", Toast.LENGTH_SHORT).show()
        }

        /**
         * 插件方法调用
         */
        override fun onEcosedMethodCall(call: PluginMethodCall, result: PluginResult) {
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
    private val mServiceDelegate: TreblePlugin = object : TreblePlugin(), ServiceConnection {

        /** 插件标题 */
        override val title: String
            get() = "ServiceDelegate"

        /** 插件通道 */
        override val channel: String
            get() = EcosedChannel.DELEGATE_CHANNEL_NAME

        /** 插件描述 */
        override val description: String
            get() = "服务功能代理, 无实际插件方法实现."

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            when (name?.className) {
//                UserService::class.kotlin.name -> {
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

                MainService::class.java.name -> {
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
//                UserService::class.kotlin.name -> {
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
//                UserService::class.kotlin.name -> {
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
//                UserService::class.kotlin.name -> {
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
    }


    /**
     * 绑定服务
     * @param context 上下文
     */
    private fun ServiceConnection.bindEcosed(context: Context) {
        try {
            if (!mIsBind) {
                context.bindService(
                    mEcosedServicesIntent,
                    this@bindEcosed,
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
    private fun ServiceConnection.unbindEcosed(context: Context) {
        try {
            if (mIsBind) {
                context.unbindService(
                    this@unbindEcosed
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
     * 构建器(伴生对象)
     */
    companion object {

        /** 用于打印日志的标签 */
        const val TAG: String = "FeOSdk"
    }
}