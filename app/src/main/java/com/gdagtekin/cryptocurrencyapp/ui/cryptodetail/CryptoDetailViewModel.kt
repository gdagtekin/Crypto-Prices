package com.gdagtekin.cryptocurrencyapp.ui.cryptodetail

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

@HiltViewModel
class CryptoDetailViewModel @Inject constructor(
    private val repository: MainRepository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    private val _cryptoMetrics = MutableStateFlow<CryptoDetailEvent>(CryptoDetailEvent.Empty)
    val cryptoMetrics: StateFlow<CryptoDetailEvent> = _cryptoMetrics

    fun getMetrics(
        id: String
    ) {
        viewModelScope.launch(dispatchers.io) {
            _cryptoMetrics.value = CryptoDetailEvent.Loading
            when (val cryptoMetricsResponse = repository.getCryptoMetrics(id)) {
                is Resource.Error -> _cryptoMetrics.value =
                    CryptoDetailEvent.Failure(cryptoMetricsResponse.message!!)
                is Resource.Success -> {
                    val cryptoMetrics = cryptoMetricsResponse.data?.data
                    _cryptoMetrics.value = CryptoDetailEvent.Success(cryptoMetrics)
                }
            }
        }
    }
}