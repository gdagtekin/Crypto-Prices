package com.gdagtekin.cryptocurrencyapp.ui.cryptodetail

import com.gdagtekin.cryptocurrencyapp.domain.models.cryptometrics.Data

sealed class CryptoDetailEvent {
    class Success(val metricsData: Data?) : CryptoDetailEvent()
    class Failure(val errorText: String) : CryptoDetailEvent()
    object Loading : CryptoDetailEvent()
    object Empty : CryptoDetailEvent()
}
