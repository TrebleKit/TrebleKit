package io.treblekit.app.hybrid

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import io.flutter.embedding.android.FlutterFragment
import io.treblekit.app.common.FlutterHost

class FlutterAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    private val mFlutter: FlutterFragment? by lazy {
        if (activity !is FlutterHost) error("未实现FlutterHost接口")
        return@lazy activity.getFlutterFragment
    }

    /** 获取 Fragment 数量 */
    override fun getItemCount() = 1

    /** 创建 Fragment */
    override fun createFragment(position: Int): Fragment {
        return mFlutter ?: error(
            message = "Flutter is null!",
        )
    }
}