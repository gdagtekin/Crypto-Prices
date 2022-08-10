package com.gdagtekin.cryptocurrencyapp.network

import com.gdagtekin.cryptocurrencyapp.domain.models.cryptolist.CryptoListResponse
import com.gdagtekin.cryptocurrencyapp.domain.models.cryptometrics.CryptoMetricsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CryptoApi {

    @GET("v2/assets?fields=id,name,symbol,metrics/market_data&limit=25")
    suspend fun getCryptoList(
        @Query("page") page: Int
    ): Response<CryptoListResponse>


    @GET("v1/assets/{id}/metrics")
    suspend fun getCryptoMetrics(
        @Path("id") id: String
    ): Response<CryptoMetricsResponse>

}