package br.com.pedromoraes.receiptview.beziercurvesplayground

import android.content.Context
import android.graphics.Canvas
import android.graphics.Outline
import android.graphics.Paint
import android.graphics.Path
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat

class BezierView3(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = ContextCompat.getColor(context, android.R.color.white)
    }
    private val path = Path()

    init {
        setWillNotDraw(false)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        generatePath()
        if (ViewCompat.getElevation(this) > 0f && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            outlineProvider = outlineProvider
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawPath(path, paint)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun getOutlineProvider(): ViewOutlineProvider {
        return object : ViewOutlineProvider() {
            override fun getOutline(view: View?, outline: Outline?) {
                outline?.setConvexPath(path)
            }

        }
    }

    private fun generatePath() {
        path.reset()
        path.moveTo(0f, 0f)
        val radious = 44.dpToPx(resources)
        val margin = 16.dpToPx(resources)
        path.lineTo(width - 2f * radious - margin, 0f)
        path.cubicTo(
            width - radious - radious * 0.6f - margin,
            0f,
            width - radious - radious * 0.6f - margin,
            radious * 0.7f,
            width - margin - radious.toFloat(),
            radious * 0.7f
        )
        path.cubicTo(
            width - radious * 0.4f - margin,
            radious * 0.7f,
            width - radious * 0.4f - margin,
            0f,
            width - margin.toFloat(),
            0f
        )
        path.lineTo(width.toFloat(), 0f)
//        path.quadTo(width - radious / 4f, radious.toFloat(), width.toFloat(), 2f * radious)
        path.lineTo(width.toFloat(), height.toFloat())
        path.lineTo(0f, height.toFloat())
        path.close()
    }
}