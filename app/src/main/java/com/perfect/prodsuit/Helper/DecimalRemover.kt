package com.perfect.prodsuit.Helper

import com.github.mikephil.charting.formatter.ValueFormatter
import kotlin.math.roundToInt


class DecimalRemover : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        return if (value == 0f) {
            "0"
        } else {
            value.roundToInt().toString() + ""
        }
    }
}