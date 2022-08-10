package com.gdagtekin.cryptocurrencyapp.domain.models.cryptometrics

data class Data(
    val _internal_temp_agora_id: String?,
    val all_time_high: AllTimeHigh?,
    val blockchain_stats_24_hours: BlockchainStats24Hours?,
    val contract_addresses: Any?,
    val id: String?,
    val market_data: MarketData?,
    val market_data_liquidity: MarketDataLiquidity?,
    val marketcap: Marketcap?,
    val name: String?,
    val serial_id: Int?,
    val slug: String?,
    val supply: Supply?,
    val symbol: String?
)