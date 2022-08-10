package com.gdagtekin.cryptocurrencyapp.ui.cryptolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gdagtekin.cryptocurrencyapp.domain.models.cryptolist.Data
import com.gdagtekin.cryptocurrencyapp.domain.util.DispatcherProvider
import com.gdagtekin.cryptocurrencyapp.domain.util.Resource
import com.gdagtekin.cryptocurrencyapp.main.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoViewModel @Inject constructor(
    private val repository: MainRepository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    private val _cryptoListing = MutableStateFlow<CryptoListEvent>(CryptoListEvent.Empty)
    val cryptoListing: StateFlow<CryptoListEvent> = _cryptoListing
    private var page: Int = 1
    private var cryptos = mutableListOf<Data>()

    init {
        onTriggerEvent(CryptoListEvent.GetCryptoList)
    }

    fun onTriggerEvent(event: CryptoListEvent) {
        when (event) {
            is CryptoListEvent.GetCryptoList -> {
                getList(page)
            }
            is CryptoListEvent.NextPage -> {
                nextPage()
            }
            is CryptoListEvent.Refresh -> {
                pageReset()
                clearCryptoList()
                getList(page)
            }
        }
    }

    private fun getList(
        page: Int
    ) {
        viewModelScope.launch(dispatchers.io) {
            _cryptoListing.value = CryptoListEvent.Loading
            when (val cryptoListResponse = repository.getCryptoList(page)) {
                is Resource.Error -> _cryptoListing.value =
                    CryptoListEvent.Failure(cryptoListResponse.message!!)
                is Resource.Success -> {
                    cryptos.addAll(cryptoListResponse.data!!.data.toMutableList())
                    _cryptoListing.value = CryptoListEvent.Success(cryptos)
                }
            }
        }
    }

    private fun nextPage() {
        pageIncrement()
        onTriggerEvent(CryptoListEvent.GetCryptoList)
    }

    private fun pageIncrement() {
        page++
    }

    private fun pageReset() {
        page = 1
    }

    private fun clearCryptoList() {
        cryptos.clear()
    }

}