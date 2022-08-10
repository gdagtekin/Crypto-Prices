package com.gdagtekin.cryptocurrencyapp.domain.models.cryptometrics

data class BlockchainStats24Hours(
    val adjusted_nvt: Double?,
    val adjusted_transaction_volume: Double?,
    val average_difficulty: Double?,
    val count_of_active_addresses: Int?,
    val count_of_blocks_added: Int?,
    val count_of_payments: Int?,
    val count_of_tx: Int?,
    val kilobytes_added: Any?,
    val median_tx_fee: Double?,
    val median_tx_value: Double?,
    val new_issuance: Double?,
    val transaction_volume: Double?
)