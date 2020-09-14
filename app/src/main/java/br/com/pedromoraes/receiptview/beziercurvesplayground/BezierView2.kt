package br.com.pedromoraes.receiptview.beziercurvesplayground

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.content.ContextCompat

class BezierView2(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = ContextCompat.getColor(context, R.color.background)
    }
    private val path = Path()

    init {
        setWillNotDraw(false)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        generatePath()
    }

    private fun generatePath() {
        path.reset()
        path.moveTo(0f, 0f)
        val radious = 20.dpToPx(resources)
        path.quadTo(radious / 4f, radious.toFloat(), radious.toFloat(), radious.toFloat())
        path.lineTo(width - radious.toFloat(), radious.toFloat())
        path.quadTo(width - radious / 4f, radious.toFloat(), width.toFloat(), 2f * radious)
        path.lineTo(width.toFloat(), height.toFloat())
        path.lineTo(0f, height.toFloat())
        path.close()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawPath(path, paint)
    }
}