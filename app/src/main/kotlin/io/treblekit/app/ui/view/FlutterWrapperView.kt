package io.treblekit.app.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import io.flutter.embedding.android.FlutterFragment
import io.treblekit.app.common.FlutterHost

class FlutterWrapperView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    /** FragmentActivity */
    private val mFragmentActivity: FragmentActivity

    /** FlutterFragment */
    private val mFlutterFragment: FlutterFragment

    init {
        if (context is FragmentActivity && context is FlutterHost) {
            mFragmentActivity = context
            mFlutterFragment = context.getFlutterFragment ?: error(
                message = "FlutterFragment为空",
            )
        } else error(
            message = "FlutterWrapperView的父Activity必须是FragmentActivity或其子类, 并且实现FlutterHost接口.",
        )
        addView(
            ViewPager2(context).apply {
                tag = FLUTTER_CONTAINER_TAG
            },
        )
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        // 遍历子View
        for (index in 0 until childCount) {
            when (index) {
                0 -> {
                    // 布局承载Flutter的ViewPager2
                    getChildAt(index).layout(
                        0,
                        0,
                        right - left,
                        bottom - top,
                    )
                }
                // 跳出循环,拒绝其他子视图布局
                else -> continue
            }
        }
    }

    override fun onViewAdded(child: View?) {
        super.onViewAdded(child)
        if (child?.tag == FLUTTER_CONTAINER_TAG && child is ViewPager2) child.apply {
            isUserInputEnabled = false // 禁用滑动
            adapter = object : FragmentStateAdapter(mFragmentActivity) {
                override fun getItemCount(): Int = 1 // 只显示一个页面
                override fun createFragment(position: Int): Fragment = mFlutterFragment
            } // 适配器
        }
    }

    private companion object {
        const val FLUTTER_CONTAINER_TAG: String = "FlutterContainer"
    }
}