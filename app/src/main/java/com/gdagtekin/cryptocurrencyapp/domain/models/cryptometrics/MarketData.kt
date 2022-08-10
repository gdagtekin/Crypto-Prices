package com.gdagtekin.cryptocurrencyapp.domain.models.cryptometrics

data class MarketData(
    val last_trade_at: String?,
    val ohlcv_last_1_hour: OhlcvLast1Hour?,
    val ohlcv_last_24_hour: OhlcvLast24Hour?,
    val percent_change_btc_last_1_hour: Double?,
    val percent_change_btc_last_24_hours: Double?,
    val percent_change_eth_last_1_hour: Double?,
    val percent_change_eth_last_24_hours: Double?,
    val percent_change_usd_last_1_hour: Double?,
    val percent_change_usd_last_24_hours: Double?,
    val price_btc: Double?,
    val price_eth: Double?,
    val price_usd: Double?,
    val real_volume_last_24_hours: Double?,
    val volume_last_24_hours: Double?,
    val volume_last_24_hours_overstatement_multiple: Double?
)