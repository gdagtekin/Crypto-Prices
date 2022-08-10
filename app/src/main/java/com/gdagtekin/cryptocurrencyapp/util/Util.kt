package com.gdagtekin.cryptocurrencyapp.util

import java.math.RoundingMode
import java.text.DecimalFormat

object Util {

    private fun formatDoublePrecision(value: Double): String {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(value).toString()
    }

    private fun formatDoubleThousandSeparator(value: Double): String {
        val df = DecimalFormat("#,###")
        df.roundingMode = RoundingMode.CEILING
        return df.format(value).toString()
    }

    private fun formatDoubleThousandSeparatorAndPrecision(value: Double): String {
        val df = DecimalFormat("#,###.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(value).toString()
    }

    fun formatDoubleThousandSeparatorWithMoneySign(value: Double): String {
        return "$" + formatDoubleThousandSeparator(value)
    }

    fun formatDoubleThousandSeparatorAndPrecisionWithMoneySign(value: Double): String {
        return "$" + formatDoubleThousandSeparatorAndPrecision(value)
    }

    fun formatDoubleWithPercentSign(value: Double): String {
        return formatDoublePrecision(value) + "%"
    }
}