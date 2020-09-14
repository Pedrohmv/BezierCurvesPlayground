package br.com.pedromoraes.receiptview.beziercurvesplayground

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import androidx.annotation.RequiresApi
import androidx.core.view.ViewCompat

class BezierView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        style = Paint.Style.FILL
    }
    var radious = 20.dpToPx(resources)
    set(value) {
        field = value.dpToPx(resources)
        generatePath()
        invalidate()
    }
    private val path = Path()
    private val firstPoint = Point()
    private val firstControlPoint = Point()
    private val firstControlPoint2 = Point()
    private val middlePoint = Point()
    private val secondControlPoint = Point()
    private val secondControlPoint2 = Point()
    private val secondPoint = Point()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        generatePath()
        if (ViewCompat.getElevation(this) > 0f && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            outlineProvider = outlineProvider
    }

    private fun generatePath() {
        firstPoint.set(width / 3, height - radious - radious / 3)
        middlePoint.set(width / 2, height)
        secondPoint.set(width * 2 / 3, height - radious - radious / 3)
        firstControlPoint.set(
            (firstPoint.x + middlePoint.x) / 2,
            firstPoint.y
        )
        firstControlPoint2.set(
            (firstPoint.x + middlePoint.x) / 2,
            middlePoint.y
        )
        secondControlPoint.set(
            (secondPoint.x + middlePoint.x) / 2,
            secondPoint.y
        )
        secondControlPoint2.set(
            (secondPoint.x + middlePoint.x) / 2,
            middlePoint.y
        )
        path.reset()
        path.moveTo(0f, 0f)
        path.lineTo(width.toFloat(), 0f)
        path.lineTo(width.toFloat(), height - radious.toFloat() - radious / 3f)
        path.lineTo(secondPoint.x.toFloat(), secondPoint.y.toFloat())
        path.cubicTo(
            secondControlPoint.x.toFloat(), secondControlPoint.y.toFloat(),
            secondControlPoint2.x.toFloat(), secondControlPoint2.y.toFloat(),
            middlePoint.x.toFloat(), middlePoint.y.toFloat()
        )
        path.cubicTo(
            firstControlPoint2.x.toFloat(), firstControlPoint2.y.toFloat(),
            firstControlPoint.x.toFloat(), firstControlPoint.y.toFloat(),
            firstPoint.x.toFloat(), firstPoint.y.toFloat()
        )
        path.lineTo(0f, height - radious.toFloat() - radious / 3f)
        path.close()
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
}