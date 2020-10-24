package ru.skillbranch.devintensive.ui.custom

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.widget.ImageView
import androidx.annotation.*
import androidx.annotation.Dimension.DP
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.toRectF
import ru.skillbranch.devintensive.App
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.extensions.dpToPx
import ru.skillbranch.devintensive.extensions.pxToDp
import java.util.logging.SocketHandler

class CircleImageView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr) {
    companion object {
        private const val DEFAULT_BORDER_COLOR = Color.WHITE
        private const val DEFAULT_BORDER_WIDTH = 2
        private const val DEFAULT_SIZE = 112
    }

    @ColorInt
    private var borderColor: Int = DEFAULT_BORDER_COLOR
    @Px
    private var borderWidth: Float = context.dpToPx(DEFAULT_BORDER_WIDTH)
    private var initials: String = ""
    @ColorInt
    private var bgColor: Int = Color.BLUE
    private val avatarPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val borderPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val initialsPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val viewRect = Rect()

    init {
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView)
            borderColor =
                    a.getColor(R.styleable.CircleImageView_cv_borderColor, DEFAULT_BORDER_COLOR)
            borderWidth = a.getDimension(
                    R.styleable.CircleImageView_cv_borderWidth,
                    context.dpToPx(DEFAULT_BORDER_WIDTH)
            )
            initials = a.getString(R.styleable.CircleImageView_cv_initials) ?: ""
            a.recycle()
        }
        scaleType = ScaleType.CENTER_CROP
        //setup()
    }

    @Dimension(unit = DP)
    fun getBorderWidth(): Int = context.pxToDp(borderWidth)

    fun setBorderWidth(@Dimension(unit = DP) dp: Int) {
        borderWidth = context.dpToPx(dp)
        borderPaint.strokeWidth = borderWidth
        invalidate()
    }

    @ColorInt
    fun getBorderColor(): Int = borderColor

    fun setBorderColor(hex: String) {
        borderColor = Color.parseColor(hex)
        borderPaint.color = borderColor
        this.invalidate()
    }

    fun setBorderColor(@ColorInt color: Int) {
        borderColor = color
        borderPaint.color = borderColor
        invalidate()
    }

    fun setBgColor(@ColorInt color: Int) = this.apply{bgColor = color}

    fun setInitials(initials: String){
        this.initials = initials
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val initSize = resolveDefaultSize(widthMeasureSpec)
        setMeasuredDimension(initSize, initSize)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        if (w == 0) return
        with(viewRect) {
            left = 0
            top = 0
            right = w
            bottom = h
        }
        prepareShader(w, h)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (drawable != null) drawAvatar(canvas)
        else drawInitials(canvas)
        drawBorder(canvas)
    }

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        prepareShader(width, height)
    }

    override fun setImageResource(@DrawableRes resId: Int) {
        super.setImageResource(resId)
        prepareShader(width, height)
    }

    private fun prepareShader(w: Int, h: Int) {
        if (w == 0 || drawable == null) return
        val srcBitmap = drawable.toBitmap(w, h, Bitmap.Config.ARGB_8888)
        avatarPaint.shader = BitmapShader(srcBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
    }

    private fun resolveDefaultSize(spec: Int): Int = when (MeasureSpec.getMode(spec)) {
        MeasureSpec.UNSPECIFIED -> context.dpToPx(DEFAULT_SIZE).toInt()
        MeasureSpec.AT_MOST -> MeasureSpec.getSize(spec)
        MeasureSpec.EXACTLY -> MeasureSpec.getSize(spec)
        else -> MeasureSpec.getSize(spec)
    }

    private fun drawAvatar(canvas: Canvas) {
        canvas.drawOval(viewRect.toRectF(), avatarPaint)
    }

    private fun drawBorder(canvas: Canvas){
        with(borderPaint) {
            color = borderColor
            style = Paint.Style.STROKE
            strokeWidth = borderWidth
        }
        val half = (borderWidth / 2).toInt()
        viewRect.inset(half, half)
        canvas.drawOval(viewRect.toRectF(), borderPaint)
    }

    private fun drawInitials(canvas: Canvas) {
        initialsPaint.color = bgColor
        canvas.drawOval(viewRect.toRectF(), initialsPaint)

        with(initialsPaint) {
            color = Color.WHITE
            textAlign = Paint.Align.CENTER
            textSize = height * 0.33f
        }

        val offsetY = (initialsPaint.descent() + initialsPaint.ascent()) / 2
        canvas.drawText(initials, viewRect.exactCenterX(), viewRect.exactCenterY() - offsetY, initialsPaint)
    }
}