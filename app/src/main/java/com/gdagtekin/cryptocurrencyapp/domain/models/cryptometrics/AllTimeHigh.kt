package com.gdagtekin.cryptocurrencyapp.domain.models.cryptometrics

data class AllTimeHigh(
    val at: String?,
    val breakeven_multiple: Double?,
    val days_since: Int?,
    val percent_down: Double?,
    val price: Double?
)