package io.treblekit.app.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageButton
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import io.treblekit.app.BuildConfig
import io.treblekit.app.R
import io.treblekit.app.ui.theme.BannerContainer
import io.treblekit.app.ui.theme.BannerContent
import io.treblekit.app.ui.theme.BannerTextSize
import io.treblekit.app.ui.theme.CapsuleContainer
import io.treblekit.app.ui.theme.CapsuleContent
import io.treblekit.app.ui.theme.CapsuleStroke
import io.treblekit.app.ui.theme.actionBarExpandedHeight
import io.treblekit.app.ui.theme.bannerDistanceOriginPointLength
import io.treblekit.app.ui.theme.bannerWidth
import io.treblekit.app.ui.theme.capsuleHeight
import io.treblekit.app.ui.theme.capsuleIndent
import io.treblekit.app.ui.theme.capsuleRadius
import io.treblekit.app.ui.theme.capsuleRightPadding
import io.treblekit.app.ui.theme.capsuleStrokeWidth
import io.treblekit.app.ui.theme.capsuleWidth
import io.treblekit.app.ui.utils.convertDpToPx
import io.treblekit.app.ui.utils.convertSpToPx
import kotlin.math.pow
import kotlin.math.sqrt

class OverlayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    private var viewWidth = 0
    private var viewHeight = 0


    /** 是否显示调试信息 */
    private val show: Boolean = BuildConfig.DEBUG


    /** 边角横幅画笔 */
    private val sdkBannerPaint: Paint = Paint(
        Paint.ANTI_ALIAS_FLAG,
    ).apply {
        color = BannerContainer.toArgb()
        style = Paint.Style.FILL
    }
    private val debugBannerPaint: Paint = Paint(
        Paint.ANTI_ALIAS_FLAG,
    ).apply {
        color = BannerContainer.toArgb()
        style = Paint.Style.FILL
    }
    private val capsuleFillPaint: Paint = Paint(
        Paint.ANTI_ALIAS_FLAG,
    ).apply {
        color = CapsuleContainer.toArgb()
        style = Paint.Style.FILL
    }
    private val capsuleStrokePaint: Paint = Paint(
        Paint.ANTI_ALIAS_FLAG,
    ).apply {
        color = CapsuleStroke.toArgb()
        style = Paint.Style.STROKE
        strokeWidth = computingCapsuleStrokeWidth.toFloat()
    }
    private val capsuleDividerPaint: Paint = Paint(
        Paint.ANTI_ALIAS_FLAG,
    ).apply {
        color = CapsuleStroke.toArgb()
        style = Paint.Style.STROKE
        strokeWidth = computingCapsuleStrokeWidth.toFloat()
    }


    /**
     * 横幅文字画笔
     */
    private val sdkBannerTextPaint: Paint = Paint(
        Paint.ANTI_ALIAS_FLAG,
    ).apply {
        color = BannerContent.toArgb()
        textSize = computingBannerTextSize.toFloat()
        style = Paint.Style.FILL
        textAlign = Paint.Align.LEFT
    }
    private val debugBannerTextPaint: Paint = Paint(
        Paint.ANTI_ALIAS_FLAG,
    ).apply {
        color = BannerContent.toArgb()
        textSize = computingBannerTextSize.toFloat()
        style = Paint.Style.FILL
        textAlign = Paint.Align.LEFT
    }


    /**
     * 横幅Path
     */
    private val sdkBannerPath: Path = Path()
    private val debugBannerPath: Path = Path()
    private val capsuleFillPath: Path = Path()
    private val capsuleStrokePath: Path = Path()


    /**
     * 真正绘制的文字,如果bannerText过长,可能会被裁剪
     */
    private var sdkBannerText: String = "TrebleKit"
    private var debugBannerText: String = "DEBUG"

    data class Point(
        val x: Float,
        val y: Float,
    )

    private val sdkBannerPointList by lazy {
        mutableListOf<Point>()
    }

    private val debugBannerPointList by lazy {
        mutableListOf<Point>()
    }

    private lateinit var mMenuButton: AppCompatImageButton
    private lateinit var mCloseButton: AppCompatImageButton


    /**
     * 初始化
     */
    init {
        ViewCompat.setOnApplyWindowInsetsListener(this) { _, insets ->
            val currentSafeInsets = insets.getInsets(
                WindowInsetsCompat.Type.systemBars(),
            )
            setPadding(
                currentSafeInsets.left,
                currentSafeInsets.top,
                currentSafeInsets.right,
                currentSafeInsets.bottom
            ) // 立即更新布局和绘制
            invalidate()
            requestLayout()
            return@setOnApplyWindowInsetsListener insets // 保持事件传递（不影响 Compose）
        } // 启用窗口插入监听（兼容 API 24+）
        ViewCompat.requestApplyInsets(this) // 初始请求插入计算
        addView(
            AppCompatImageButton(context).apply {
                tag = MENU_BUTTON_TAG
            },
            LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT,
            ),
        ) // 添加菜单按钮
        addView(
            AppCompatImageButton(context).apply {
                tag = CLOSE_BUTTON_TAG
            },
            LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT,
            ),
        ) // 添加关闭按钮
        setWillNotDraw(false) // 启用内容绘制
    }

    override fun onViewAdded(child: View?) {
        super.onViewAdded(child)
        child?.let { childView ->
            when (childView.tag) {
                MENU_BUTTON_TAG -> (childView as AppCompatImageButton).apply {
                    setImageResource(R.drawable.baseline_more_horiz_24)
                    setBackgroundColor(Color.Transparent.toArgb())
                    setColorFilter(
                        CapsuleContent.toArgb(), PorterDuff.Mode.SRC_IN
                    )
//                    setOnClickListener {
//                        Toast.makeText(
//                            child.context,
//                            "mMenuButton",
//                            Toast.LENGTH_SHORT,
//                        ).show()
//                    }
                    mMenuButton = this@apply
                }

                CLOSE_BUTTON_TAG -> (childView as AppCompatImageButton).apply {
                    setImageResource(R.drawable.baseline_close_24)
                    setBackgroundColor(Color.Transparent.toArgb())
                    setColorFilter(
                        CapsuleContent.toArgb(), PorterDuff.Mode.SRC_IN
                    )
//                    setOnClickListener {
//                        Toast.makeText(
//                            child.context,
//                            "mCloseButton",
//                            Toast.LENGTH_SHORT,
//                        ).show()
//                    }
                    mCloseButton = this@apply
                }

                else -> error(
                    message = "unknown instance"
                )
            }
        }
    }

    internal fun setMenuOnClickListener(listener: OnClickListener) {
        mMenuButton.setOnClickListener(listener)
    }

    internal fun setCloseOnClickListener(listener: OnClickListener) {
        mCloseButton.setOnClickListener(listener)
    }

    /**
     * 布局
     *
     * @param changed 视图是否发生了变化
     * @param left 视图的左侧坐标
     * @param top 视图的顶部坐标
     * @param right 视图的右侧坐标
     * @param bottom 视图的底部坐标
     */
    override fun onLayout(
        changed: Boolean,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int,
    ) {
        // 起始位置偏移量，用于放置第一个按钮
        var leftOffset = 0
        // 遍历子视图
        for (index in 0 until childCount) {
            when (index) {
                // 仅布局前两个子视图
                0, 1 -> {
                    // 布局子视图
                    getChildAt(index).layout(
                        // 按钮的左侧坐标
                        // 视图宽度 - 视图右边距 - 胶囊按钮右边距 - 胶囊按钮宽度 + 左侧偏移量
                        viewWidth - paddingRight - computingCapsuleRightPadding - computingCapsuleWidth + leftOffset,
                        // 按钮的顶部坐标
                        // 胶囊按钮顶部边距 + 视图顶部边距
                        computingCapsuleTopPadding + paddingTop,
                        // 按钮的右侧坐标
                        // 视图宽度 - 视图右边距 - 胶囊按钮右边距 - 胶囊按钮宽度 + 左侧偏移量 + 胶囊按钮宽度的一半
                        viewWidth - paddingRight - computingCapsuleRightPadding - computingCapsuleWidth + leftOffset + computingCapsuleWidth / 2,
                        // 按钮的底部坐标
                        // 胶囊按钮顶部边距 + 胶囊按钮高度 + 视图顶部边距
                        computingCapsuleTopPadding + computingCapsuleHeight + paddingTop,
                    )
                    // 更新下一个按钮的起始位置
                    leftOffset += computingCapsuleWidth / 2
                }
                // 跳出循环,拒绝其他子视图布局
                else -> continue
            }
        }
    }

    /**
     * 绘制
     *
     * @param canvas 画布对象
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 绘制胶囊按钮背景
        drawCapsuleFill(canvas)
        drawCapsuleStroke(canvas)
        drawCapsuleDivider(canvas)
    }

    /**
     * 绘制前景内容
     *
     * @param canvas 画布对象
     */
    override fun onDrawForeground(canvas: Canvas) {
        super.onDrawForeground(canvas)
        if (show) {
            // 绘制SDK标识角标
            val sdkPointList = generatorSdkPointByPosition()
            drawSdkBanner(canvas, sdkPointList)
            drawSdkText(canvas, sdkPointList)
            // 绘制调试模式标识角标
            val debugPointList = generatorDebugPointByPosition()
            drawDebugBanner(canvas, debugPointList)
            drawDebugText(canvas, debugPointList)
        }
    }

    /**
     * 设置尺寸
     *
     * @param width 宽度
     * @param height 高度
     * @param oldWidth 旧宽度
     * @param oldHeight 旧高度
     */
    override fun onSizeChanged(
        width: Int,
        height: Int,
        oldWidth: Int,
        oldHeight: Int,
    ) {
        super.onSizeChanged(
            width,
            height,
            oldWidth,
            oldHeight,
        )
        viewWidth = width
        viewHeight = height
    }

    /**
     * 禁用触摸事件拦截
     *
     * @param ev 触摸事件
     * @return 是否拦截事件
     */
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return false
    }

    private fun generatorSdkPointByPosition(): List<Point> {
        sdkBannerPointList.clear()
        sdkBannerPointList.add(
            Point(
                x = 0F,
                y = computingBannerDistanceOriginPointLength - computingBannerWidth.toFloat(),
            ),
        )
        sdkBannerPointList.add(
            Point(
                x = computingBannerDistanceOriginPointLength - computingBannerWidth.toFloat(),
                y = 0F,
            ),
        )
        sdkBannerPointList.add(
            Point(
                x = computingBannerDistanceOriginPointLength.toFloat(),
                y = 0F,
            ),
        )
        sdkBannerPointList.add(
            Point(
                x = 0F,
                y = computingBannerDistanceOriginPointLength.toFloat(),
            ),
        )
        return sdkBannerPointList
    }


    /**
     * 绘制横幅
     */
    private fun drawSdkBanner(canvas: Canvas, pointList: List<Point>) {
        sdkBannerPath.apply {
            reset()
            pointList.withIndex().forEach {
                if (it.index == 0) {
                    moveTo(it.value.x, it.value.y)
                } else {
                    lineTo(it.value.x, it.value.y)
                }
            }
        }
        canvas.drawPath(sdkBannerPath, sdkBannerPaint)
    }

    /**
     * 绘制横幅上的文字
     */
    private fun drawSdkText(canvas: Canvas, pointList: List<Point>) {
        // 测量欲绘制文字宽度
        val bannerTextWidth = sdkBannerTextPaint.measureText(sdkBannerText)
        // 计算banner最短边长度
        val bannerShortestLength = (sqrt(
            2 * (computingBannerDistanceOriginPointLength - computingBannerWidth).toDouble().pow(2)
        )).toFloat()
        if (bannerTextWidth > bannerShortestLength) {
            // 如果最短边长度小于欲绘制文字长度,则对欲绘制文字剪裁,直到欲绘制文字比最短边长度小方可绘制文字
            sdkBannerText = sdkBannerText.substring(0, sdkBannerText.length - 1)
            drawSdkText(canvas, pointList)
            return
        }
        // 计算banner最长边长度
        val bannerLongestLength =
            (sqrt(2 * (computingBannerDistanceOriginPointLength).toDouble().pow(2))).toFloat()
        val hOffset = bannerShortestLength / 2 - bannerTextWidth / 2
        // 单个直角边长度
        val oneOfTheRightAngleLength = (bannerLongestLength - bannerShortestLength) / 2
        // 计算banner的高度
        val bannerHeight = sqrt(
            computingBannerWidth.toDouble().pow(2) - oneOfTheRightAngleLength.pow(2)
        ).toFloat()
        val fontMetrics = sdkBannerTextPaint.fontMetrics
        // 计算baseLine偏移量
        val baseLineOffset = (fontMetrics.top + fontMetrics.bottom) / 2
        val vOffset = bannerHeight / 2 - baseLineOffset
        canvas.drawTextOnPath(
            sdkBannerText,
            sdkBannerPath,
            hOffset,
            vOffset,
            sdkBannerTextPaint,
        )
    }


    private fun generatorDebugPointByPosition(): List<Point> {
        debugBannerPointList.clear()
        debugBannerPointList.add(
            element = Point(
                x = viewWidth - (computingBannerDistanceOriginPointLength - computingBannerWidth).toFloat(),
                y = 0F,
            ),
        )
        debugBannerPointList.add(
            element = Point(
                x = viewWidth.toFloat(),
                y = (computingBannerDistanceOriginPointLength - computingBannerWidth).toFloat(),
            ),
        )
        debugBannerPointList.add(
            element = Point(
                x = viewWidth.toFloat(),
                y = computingBannerDistanceOriginPointLength.toFloat(),
            ),
        )
        debugBannerPointList.add(
            element = Point(
                x = (viewWidth - computingBannerDistanceOriginPointLength).toFloat(),
                y = 0F,
            ),
        )
        return debugBannerPointList
    }

    private fun drawDebugBanner(canvas: Canvas, pointList: List<Point>) {
        debugBannerPath.apply {
            reset()
            pointList.withIndex().forEach {
                if (it.index == 0) {
                    moveTo(it.value.x, it.value.y)
                } else {
                    lineTo(it.value.x, it.value.y)
                }
            }
        }
        canvas.drawPath(debugBannerPath, debugBannerPaint)
    }

    private fun drawDebugText(canvas: Canvas, pointList: List<Point>) {
        // 测量欲绘制文字宽度
        val bannerTextWidth = debugBannerTextPaint.measureText(debugBannerText)
        // 计算banner最短边长度
        val bannerShortestLength = (sqrt(
            2 * (computingBannerDistanceOriginPointLength - computingBannerWidth).toDouble().pow(2)
        )).toFloat()
        if (bannerTextWidth > bannerShortestLength) {
            // 如果最短边长度小于欲绘制文字长度,则对欲绘制文字剪裁,直到欲绘制文字比最短边长度小方可绘制文字
            debugBannerText = debugBannerText.substring(0, debugBannerText.length - 1)
            drawDebugText(canvas, pointList)
            return
        }
        // 计算banner最长边长度
        val bannerLongestLength =
            (sqrt(2 * (computingBannerDistanceOriginPointLength).toDouble().pow(2))).toFloat()
        val hOffset = bannerShortestLength / 2 - bannerTextWidth / 2
        // 单个直角边长度
        val oneOfTheRightAngleLength = (bannerLongestLength - bannerShortestLength) / 2
        // 计算banner的高度
        val bannerHeight = sqrt(
            computingBannerWidth.toDouble().pow(2) - oneOfTheRightAngleLength.pow(2)
        ).toFloat()
        val fontMetrics = debugBannerTextPaint.fontMetrics
        // 计算baseLine偏移量
        val baseLineOffset = (fontMetrics.top + fontMetrics.bottom) / 2
        val vOffset = bannerHeight / 2 - baseLineOffset
        canvas.drawTextOnPath(
            debugBannerText,
            debugBannerPath,
            hOffset,
            vOffset,
            debugBannerTextPaint,
        )
    }

    private fun drawCapsuleFill(canvas: Canvas) {
        setRoundRect(capsuleFillPath)
        canvas.drawPath(capsuleFillPath, capsuleFillPaint)
    }

    private fun drawCapsuleStroke(canvas: Canvas) {
        setRoundRect(capsuleStrokePath)
        canvas.drawPath(capsuleStrokePath, capsuleStrokePaint)
    }

    private fun drawCapsuleDivider(canvas: Canvas) {
        canvas.drawLine(
            (viewWidth - (computingCapsuleWidth / 2) - computingCapsuleRightPadding - paddingRight).toFloat(),
            (computingCapsuleTopPadding + paddingTop + computingCapsuleDividerIndent).toFloat(),//4dp
            (viewWidth - (computingCapsuleWidth / 2) - computingCapsuleRightPadding - paddingRight).toFloat(),
            (computingCapsuleTopPadding + computingCapsuleHeight + paddingTop - computingCapsuleDividerIndent).toFloat(),
            capsuleDividerPaint,
        )
    }

    private fun setRoundRect(path: Path) {
        path.reset()
        path.addRoundRect(
            RectF(
                (viewWidth - computingCapsuleWidth - computingCapsuleRightPadding - paddingRight).toFloat(),
                (computingCapsuleTopPadding + paddingTop).toFloat(),
                (viewWidth - computingCapsuleRightPadding - paddingRight).toFloat(),
                (computingCapsuleTopPadding + computingCapsuleHeight + paddingTop).toFloat()
            ),
            computingCapsuleRadius.toFloat(),
            computingCapsuleRadius.toFloat(),
            Path.Direction.CW,
        )
    }


    private val computingCapsuleWidth: Int
        get() = convertDpToPx(
            context = context,
            value = capsuleWidth,
        )

    private val computingCapsuleHeight: Int
        get() = convertDpToPx(
            context = context,
            value = capsuleHeight,
        )

    /** 胶囊顶部边距 */
    private val computingCapsuleTopPadding: Int
        get() = (computingActionBarExpandedHeight - computingCapsuleHeight) / 2

    private val computingCapsuleRightPadding: Int
        get() = convertDpToPx(
            context = context,
            value = capsuleRightPadding,
        )

    private val computingCapsuleDividerIndent: Int
        get() = convertDpToPx(
            context = context,
            value = capsuleIndent,
        )

    private val computingCapsuleRadius: Int
        get() = convertDpToPx(
            context = context,
            value = capsuleRadius,
        )

    private val computingActionBarExpandedHeight: Int
        get() = convertDpToPx(
            context = context,
            value = actionBarExpandedHeight,
        )

    private val computingBannerDistanceOriginPointLength: Int
        get() = convertDpToPx(
            context = context,
            value = bannerDistanceOriginPointLength,
        )

    private val computingBannerWidth: Int
        get() = convertDpToPx(
            context = context,
            value = bannerWidth,
        )

    private val computingCapsuleStrokeWidth: Int
        get() = convertDpToPx(
            context = context,
            value = capsuleStrokeWidth,
        )

    private val computingBannerTextSize: Int
        get() = convertSpToPx(
            context = context,
            value = BannerTextSize,
        )

    companion object {
        private const val MENU_BUTTON_TAG: String = "menu"
        private const val CLOSE_BUTTON_TAG: String = "close"
    }
}