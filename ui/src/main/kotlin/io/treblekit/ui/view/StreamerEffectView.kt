package io.treblekit.ui.view

import android.content.Context
import android.content.res.Resources
import android.graphics.RenderEffect
import android.graphics.RuntimeShader
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import io.treblekit.resources.ResGL
import io.treblekit.utils.loadShader

internal class StreamerEffectView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    private val startTime = System.nanoTime().toFloat()
    private val mHandler = Handler(Looper.getMainLooper())
    private lateinit var bound: FloatArray
    private lateinit var mBackgroundShader: RuntimeShader
    private var mResources: Resources = context.resources
    private lateinit var uResolution: FloatArray
    private var uAnimTime = (System.nanoTime().toFloat()) / 1.0E9f
    private var uBgBound = floatArrayOf(0.0f, 0.4489f, 1.0f, 0.5511f)
    private val uTranslateY = 0.0f
    private var uPoints = floatArrayOf(
        0.67f, 0.42f, 1.0f, 0.69f, 0.75f, 1.0f, 0.14f, 0.71f, 0.95f, 0.14f, 0.27f, 0.8f
    )
    private var uColors = generateRandomColors()
    private val uAlphaMulti = 1.0f
    private val uNoiseScale = 1.5f
    private val uPointOffset = 0.1f
    private val uPointRadiusMulti = 1.0f
    private var uSaturateOffset = 0.2f
    private var uLightOffset = 0.1f
    private val uAlphaOffset = 0.5f
    private val uShadowColorMulti = 0.3f
    private val uShadowColorOffset = 0.3f
    private val uShadowNoiseScale = 5.0f
    private val uShadowOffset = 0.01f
    private var startColors: FloatArray = uColors.copyOf()
    private var targetColors: FloatArray = uColors.copyOf()
    private var transitionStartTime: Long = 0L
    private var isTransitioning = false
    private var colorChangeHandler: Handler? = null
    private var colorRunnable: Runnable? = null

    init {
        initShader()
        applyEffect(context)
    }

    private fun initShader() {
        val shader: String? = loadShader(resources = mResources, id = ResGL.STREAMER_EFFECT)
        mBackgroundShader = RuntimeShader(shader!!)
        mBackgroundShader.setFloatUniform("uTranslateY", uTranslateY)
        mBackgroundShader.setFloatUniform("uPoints", uPoints)
        mBackgroundShader.setFloatUniform("uColors", uColors)
        mBackgroundShader.setFloatUniform("uNoiseScale", uNoiseScale)
        mBackgroundShader.setFloatUniform("uPointOffset", uPointOffset)
        mBackgroundShader.setFloatUniform("uPointRadiusMulti", uPointRadiusMulti)
        mBackgroundShader.setFloatUniform("uSaturateOffset", uSaturateOffset)
        mBackgroundShader.setFloatUniform("uShadowColorMulti", uShadowColorMulti)
        mBackgroundShader.setFloatUniform("uShadowColorOffset", uShadowColorOffset)
        mBackgroundShader.setFloatUniform("uShadowOffset", uShadowOffset)
        mBackgroundShader.setFloatUniform("uBound", uBgBound)
        mBackgroundShader.setFloatUniform("uAlphaMulti", uAlphaMulti)
        mBackgroundShader.setFloatUniform("uLightOffset", uLightOffset)
        mBackgroundShader.setFloatUniform("uAlphaOffset", uAlphaOffset)
        mBackgroundShader.setFloatUniform("uShadowNoiseScale", uShadowNoiseScale)
    }

    private fun applyEffect(context: Context) = post {
        showRuntimeShader(context, this@StreamerEffectView)
        mHandler.post(mRunnableBgEffect)
    }

    private var mRunnableBgEffect: Runnable = Runnable {
        setAnimTime((((System.nanoTime().toFloat()) - startTime) / 1.0E9f) % 62.831852f)
        setResolution(floatArrayOf(width.toFloat(), height.toFloat()))
        updateMaterials()
        setRenderEffect(RenderEffect.createRuntimeShaderEffect(mBackgroundShader, "uTex"))
        mHandler.postDelayed(mRunnableBgEffect, 16L)
    }

    private fun updateMaterials() {
        mBackgroundShader.setFloatUniform("uAnimTime", uAnimTime)
        mBackgroundShader.setFloatUniform("uResolution", uResolution)
    }

    private fun setAnimTime(f: Float) {
        uAnimTime = f
    }

    private fun setColors(fArr: FloatArray) {
        uColors = fArr
        mBackgroundShader.setFloatUniform("uColors", fArr)
    }

    private fun setPoints(fArr: FloatArray) {
        uPoints = fArr
        mBackgroundShader.setFloatUniform("uPoints", fArr)
    }

    private fun setBound(fArr: FloatArray) {
        this.uBgBound = fArr
        this.mBackgroundShader.setFloatUniform("uBound", fArr)
    }

    private fun setLightOffset() {
        this.uLightOffset = -0.1f
        this.mBackgroundShader.setFloatUniform("uLightOffset", -0.1f)
    }

    private fun setSaturateOffset() {
        this.uSaturateOffset = 0.2f
        this.mBackgroundShader.setFloatUniform("uSaturateOffset", 0.2f)
    }

    private fun setPhoneDark(fArr: FloatArray) {
        setLightOffset()
        setSaturateOffset()
        setPoints(
            floatArrayOf(
                0.63f, 0.5f, 0.88f, 0.69f, 0.75f, 0.8f, 0.17f, 0.66f, 0.81f, 0.14f, 0.24f, 0.72f
            )
        )
        setColors(generateRandomColors())
        setBound(fArr)
        startColorAnimation()
    }

    private fun startColorAnimation() {
        colorChangeHandler = Handler(Looper.getMainLooper())
        colorRunnable = object : Runnable {
            override fun run() {
                startColors = uColors.copyOf()
                targetColors = generateRandomColors(isDark = true)
                transitionStartTime = System.currentTimeMillis()
                isTransitioning = true
                runTransitionFrame()
                colorChangeHandler?.postDelayed(this, 3000)
            }
        }
        colorChangeHandler?.post(colorRunnable!!)
    }

    private fun runTransitionFrame() {
        if (!isTransitioning) return

        val duration = 3000f
        val elapsed = System.currentTimeMillis() - transitionStartTime
        val t = (elapsed / duration).coerceIn(0f, 1f)

        val interpolatedColors = FloatArray(startColors.size)
        for (i in startColors.indices) {
            interpolatedColors[i] = startColors[i] * (1 - t) + targetColors[i] * t
        }
        setColors(interpolatedColors)

        if (t < 1f) {
            // 下一帧
            Handler(Looper.getMainLooper()).postDelayed(
                { runTransitionFrame() },
                16L,
            ) // 约 60fps
        } else {
            isTransitioning = false
        }
    }

    private fun generateRandomColors(count: Int = 4, isDark: Boolean = false): FloatArray {
        val colors = FloatArray(size = count * 4)
        for (i in 0 until count) {
            val base = if (isDark) 0.0 else 0.3
            val range = if (isDark) 0.5 else 0.7
            val r = (base + Math.random() * range).toFloat()
            val g = (base + Math.random() * range).toFloat()
            val b = (base + Math.random() * range).toFloat()
            val a = 1.0f
            colors[i * 4] = r
            colors[i * 4 + 1] = g
            colors[i * 4 + 2] = b
            colors[i * 4 + 3] = a
        }
        return colors
    }

    private fun setResolution(fArr: FloatArray) {
        uResolution = fArr
    }

    private fun showRuntimeShader(context: Context, view: View) {
        calcAnimationBound(context, view)
        setPhoneDark(bound)
    }

    private fun calcAnimationBound(context: Context, view: View) {
        val height = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            416f,
            context.resources.displayMetrics,
        )
        val height2 = height / (view.parent as ViewGroup).height
        val width = (view.parent as ViewGroup).width.toFloat()
        bound = if (width <= height) {
            floatArrayOf(0.0f, 1.0f - height2, 1.0f, height2)
        } else {
            floatArrayOf(
                ((width - height) / 2.0f) / width, 1.0f - height2, height / width, height2
            )
        }
    }
}