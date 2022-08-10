package com.gdagtekin.cryptocurrencyapp.domain.models.cryptometrics

data class Supply(
    val annual_inflation_percent: Double?,
    val circulating: Double?,
    val liquid: Double?,
    val stock_to_flow: Double?,
    val supply_revived_90d: Double?,
    val y_2050: Double?,
    val y_2050_issued_percent: Double?,
    val y_plus10: Double?,
    val y_plus10_issued_percent: Double?
)