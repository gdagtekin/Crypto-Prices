package com.gdagtekin.cryptocurrencyapp.ui.cryptolist

import com.gdagtekin.cryptocurrencyapp.domain.models.cryptolist.Data

sealed class CryptoListEvent {
    class Success(val cryptos: List<Data>): CryptoListEvent()
    class Failure(val errorText: String): CryptoListEvent()
    object Loading : CryptoListEvent()
    object Empty : CryptoListEvent()
    object NextPage: CryptoListEvent()
    object GetCryptoList: CryptoListEvent()
    object Refresh: CryptoListEvent()
}
