package com.gdagtekin.cryptocurrencyapp.domain.models.cryptolist

data class OhlcvLast24Hour(
    val close: Double,
    val high: Double,
    val low: Double,
    val `open`: Double,
    val volume: Double
)