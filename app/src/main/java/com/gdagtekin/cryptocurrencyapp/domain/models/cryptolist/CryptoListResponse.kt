package com.gdagtekin.cryptocurrencyapp.domain.models.cryptolist

data class CryptoListResponse(
    val `data`: List<Data>,
    val status: Status
)