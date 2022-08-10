package com.gdagtekin.cryptocurrencyapp.domain.models.cryptometrics

data class Marketcap(
    val current_marketcap_usd: Double?,
    val liquid_marketcap_usd: Double?,
    val marketcap_dominance_percent: Double?,
    val outstanding_marketcap_usd: Double?,
    val rank: Int?,
    val realized_marketcap_usd: Double?,
    val volume_turnover_last_24_hours_percent: Double?,
    val y_2050_marketcap_usd: Double?,
    val y_plus10_marketcap_usd: Double?
)