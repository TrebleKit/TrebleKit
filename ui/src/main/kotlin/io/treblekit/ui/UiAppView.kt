package io.treblekit.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import io.treblekit.ui.view.HybridComposeView

class UiAppView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    private val mContext: Context = context

    private val mContentView: View = HybridComposeView(
        context = mContext,
    )

    init {
        addView(mContentView)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        for (index in 0 until childCount) {
            when (index) {
                0 -> {
                    getChildAt(index).layout(
                        0,
                        0,
                        right - left,
                        bottom - top,
                    )
                }

                else -> continue
            }
        }
    }
}