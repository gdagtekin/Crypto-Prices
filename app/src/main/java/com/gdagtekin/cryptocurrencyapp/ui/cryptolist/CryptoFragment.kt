package com.gdagtekin.cryptocurrencyapp.ui.cryptolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewbinding.ViewBinding
import com.gdagtekin.cryptocurrencyapp.R
import com.gdagtekin.cryptocurrencyapp.databinding.FragmentCryptoBinding
import com.gdagtekin.cryptocurrencyapp.domain.models.cryptolist.Data
import com.gdagtekin.cryptocurrencyapp.ui.BindingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber

@AndroidEntryPoint
class CryptoFragment : BindingFragment<FragmentCryptoBinding>(),
    CryptoAdapter.Interaction,
    SwipeRefreshLayout.OnRefreshListener {

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentCryptoBinding::inflate

    private val viewModel: CryptoViewModel by viewModels()

    private var recyclerAdapter: CryptoAdapter? = null // can leak memory so need to null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        binding.swipeRefresh.setOnRefreshListener(this)

        lifecycleScope.launchWhenStarted {
            viewModel.cryptoListing.collect { event ->
                when (event) {
                    is CryptoListEvent.Success -> {
                        binding.currenciesProgressBar.isVisible = false

                        recyclerAdapter?.apply {
                            cryptoItems = event.cryptos
                        }
                        recyclerAdapter?.notifyDataSetChanged()
                    }
                    is CryptoListEvent.Failure -> {
                        binding.currenciesProgressBar.isVisible = false
                        Timber.i(event.errorText)
                    }
                    is CryptoListEvent.Loading -> {
                        binding.currenciesProgressBar.isVisible = true
                    }
                    else -> Unit
                }

            }
        }
    }

    private fun initRecyclerView() {
        binding.cryptoRecyclerview.apply {
            layoutManager = LinearLayoutManager(this@CryptoFragment.context)

            recyclerAdapter = CryptoAdapter(this@CryptoFragment)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val lastPosition = layoutManager.findLastVisibleItemPosition()

                    if (
                        lastPosition == recyclerAdapter?.itemCount?.minus(1)
                    ) {
                        viewModel.onTriggerEvent(CryptoListEvent.NextPage)
                    }
                }
            })
            adapter = recyclerAdapter
        }
    }

    override fun onItemSelected(position: Int, item: Data) {
        findNavController().findDestination(R.id.action_main_to_nav_metrics)?.label = item.name
        findNavController().navigate(R.id.action_main_to_nav_metrics, bundleOf("id" to item.id))
    }

    override fun onRefresh() {
        recyclerAdapter?.clearList()
        viewModel.onTriggerEvent(CryptoListEvent.Refresh)
        binding.swipeRefresh.isRefreshing = false
    }

}