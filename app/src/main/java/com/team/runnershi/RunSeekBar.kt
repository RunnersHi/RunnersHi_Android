package com.team.runnershi

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.core.content.res.ResourcesCompat
import android.graphics.Rect
import android.graphics.Point


class RunSeekBar(context: Context): AppCompatSeekBar(context) {
    private val TAG = RunSeekBar::class.simpleName
    private val view = LayoutInflater.from(context).inflate(R.layout.ly_seekbar_thumb, null, false)

    private lateinit var labelBackground: Bitmap
    private lateinit var labelTextPaint: Paint
    private lateinit var labelBackgroundPaint: Paint
    private lateinit var barBounds: Rect
    private lateinit var labelTextRect: Rect
    private lateinit var labelPos: Point

    private val thumbDrawable = ResourcesCompat.getDrawable(resources, R.drawable.smallwhitebox_runactivity_runnerprofile, null)

    init{
        thumbDrawable?.let{
            Log.d(TAG, "thumbDrawable is null")
            labelBackground = drawableToBitmap(thumbDrawable)

            labelTextPaint = Paint()
            labelTextPaint.apply{
                this.color = Color.BLACK
                this.textSize = (12 * 0.0624).toFloat()
                this.letterSpacing =  (18 * 0.0624).toFloat()
                this.isDither = true

            }

            labelBackgroundPaint = Paint()
            labelTextRect = Rect()
            labelPos = Point()
            barBounds = Rect()



        }

    }
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val viewWidth = widthMeasureSpec
        val barHeight = heightMeasureSpec
        setMeasuredDimension(viewWidth, barHeight + labelBackground.getHeight())

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    fun drawableToBitmap(drawable: Drawable): Bitmap {
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }
}