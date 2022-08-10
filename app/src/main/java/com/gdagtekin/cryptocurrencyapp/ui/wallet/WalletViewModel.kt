package com.gdagtekin.cryptocurrencyapp.ui.wallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gdagtekin.cryptocurrencyapp.domain.util.DispatcherProvider
import com.gdagtekin.cryptocurrencyapp.domain.util.Resource
import com.gdagtekin.cryptocurrencyapp.main.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * It's empty for now, maybe I'll develop it in the future.
 */

@HiltViewModel
class WalletViewModel @Inject constructor(
    private val repository: MainRepository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    private val _cryptoListing = MutableStateFlow<WalletEvent>(WalletEvent.Empty)
    val cryptoListing: StateFlow<WalletEvent> = _cryptoListing

    fun getList(
        page: Int
    ) {
        viewModelScope.launch(dispatchers.io) {
            _cryptoListing.value = WalletEvent.Loading
            when(val cryptoListResponse = repository.getCryptoList(page)) {
                is Resource.Error -> _cryptoListing.value = WalletEvent.Failure(cryptoListResponse.message!!)
                is Resource.Success -> {
                    val cryptos = cryptoListResponse.data!!.data
                    _cryptoListing.value = WalletEvent.Success(cryptos)
                }
            }
        }
    }

}