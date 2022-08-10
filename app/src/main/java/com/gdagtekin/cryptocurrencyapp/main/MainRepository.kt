package com.gdagtekin.cryptocurrencyapp.main

import com.gdagtekin.cryptocurrencyapp.domain.models.cryptolist.CryptoListResponse
import com.gdagtekin.cryptocurrencyapp.domain.models.cryptometrics.CryptoMetricsResponse
import com.gdagtekin.cryptocurrencyapp.domain.util.Resource

interface MainRepository {
    suspend fun getCryptoList(page: Int): Resource<CryptoListResponse>
    suspend fun getCryptoMetrics(id: String): Resource<CryptoMetricsResponse>
}