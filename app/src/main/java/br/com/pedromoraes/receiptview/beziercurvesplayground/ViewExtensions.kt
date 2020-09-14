package br.com.pedromoraes.receiptview.beziercurvesplayground

import android.content.res.Resources

fun Int.dpToPx(resources: Resources): Int =
    (resources.displayMetrics.density * this).toInt()