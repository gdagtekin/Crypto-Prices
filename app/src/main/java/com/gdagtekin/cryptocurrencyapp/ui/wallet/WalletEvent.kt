package com.gdagtekin.cryptocurrencyapp.ui.wallet

import com.gdagtekin.cryptocurrencyapp.domain.models.cryptolist.Data

sealed class WalletEvent {
    class Success(val cryptos: List<Data>): WalletEvent()
    class Failure(val errorText: String): WalletEvent()
    object Loading : WalletEvent()
    object Empty : WalletEvent()
}
