package com.gdagtekin.cryptocurrencyapp.main

import com.gdagtekin.cryptocurrencyapp.domain.models.cryptolist.CryptoListResponse
import com.gdagtekin.cryptocurrencyapp.domain.models.cryptometrics.CryptoMetricsResponse
import com.gdagtekin.cryptocurrencyapp.domain.util.Resource
import com.gdagtekin.cryptocurrencyapp.network.CryptoApi
import javax.inject.Inject

class DefaultMainRepository @Inject constructor(
    private val api: CryptoApi
) : MainRepository {

    override suspend fun getCryptoList(page: Int): Resource<CryptoListResponse> {
        return try {
            val response = api.getCryptoList(page)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }

    override suspend fun getCryptoMetrics(id: String): Resource<CryptoMetricsResponse> {
        return try {
            val response = api.getCryptoMetrics(id)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }
}