package com.gdagtekin.cryptocurrencyapp.ui.wallet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.gdagtekin.cryptocurrencyapp.databinding.FragmentWalletBinding
import com.gdagtekin.cryptocurrencyapp.ui.BindingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber

/**
 * It's empty for now, maybe I'll develop it in the future.
 */

@AndroidEntryPoint
class WalletFragment : BindingFragment<FragmentWalletBinding>() {

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentWalletBinding::inflate

    private val viewModel: WalletViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenStarted {
            viewModel.cryptoListing.collect{ event ->
                when(event) {
                    is WalletEvent.Success -> {
                        Timber.i("onViewCreated: WalletEvent.Success")
                    }
                    is WalletEvent.Failure -> {
                        Timber.i("onViewCreated: WalletEvent.Failure")
                    }
                    is WalletEvent.Loading -> {
                        Timber.i("onViewCreated: WalletEvent.Loading")
                    }
                    else -> Unit
                }

            }
        }
    }

}