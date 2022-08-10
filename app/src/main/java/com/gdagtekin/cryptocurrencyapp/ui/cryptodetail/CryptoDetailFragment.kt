package com.gdagtekin.cryptocurrencyapp.ui.cryptodetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.gdagtekin.cryptocurrencyapp.R
import com.gdagtekin.cryptocurrencyapp.databinding.CryptoDetailFragmentBinding
import com.gdagtekin.cryptocurrencyapp.domain.models.cryptometrics.Data
import com.gdagtekin.cryptocurrencyapp.ui.BindingFragment
import com.gdagtekin.cryptocurrencyapp.util.Util
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class CryptoDetailFragment : BindingFragment<CryptoDetailFragmentBinding>() {

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = CryptoDetailFragmentBinding::inflate

    private val viewModel: CryptoDetailViewModel by viewModels()
    private var time = 0L
    private var timeForShimmer = 500

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getString("id", null)

        id?.let {
            viewModel.getMetrics(it)
        }

        lifecycleScope.launchWhenStarted {
            viewModel.cryptoMetrics.collect { event ->
                when (event) {
                    is CryptoDetailEvent.Success -> {
                        Timber.i(event.metricsData.toString())

                        event.metricsData?.let { dataToUI(it) }
                    }
                    is CryptoDetailEvent.Failure -> {
                        Timber.i(event.errorText)
                    }
                    is CryptoDetailEvent.Loading -> {
                        time = System.currentTimeMillis()
                        binding.rlCryptoDetailShimmer.visibility = View.VISIBLE
                        binding.rlCryptoIconAndTopData.visibility = View.GONE
                    }
                    else -> Unit
                }

            }
        }

    }

    private val requestOptions = RequestOptions
        .placeholderOf(R.drawable.ic_currencies)
        .error(R.drawable.ic_currencies)

    private fun dataToUI(metricsData: Data) {
        metricsData.symbol?.let { binding.tvCryptoSymbol.text = it }

        metricsData.marketcap?.rank?.let {
            binding.tvCryptoRank.text = getString(R.string.rank) + it
        }
        metricsData.market_data?.price_usd?.let {
            binding.tvCryptoPrice.text =
                Util.formatDoubleThousandSeparatorAndPrecisionWithMoneySign(it)
        }
        metricsData.market_data?.ohlcv_last_24_hour?.low?.let {
            binding.tvCrypto24HLowPrice.text =
                Util.formatDoubleThousandSeparatorAndPrecisionWithMoneySign(it)
        }
        metricsData.market_data?.ohlcv_last_24_hour?.high?.let {
            binding.tvCrypto24HHighPrice.text =
                Util.formatDoubleThousandSeparatorAndPrecisionWithMoneySign(it)
        }
        metricsData.marketcap?.current_marketcap_usd?.let {
            binding.tvMarketCap.text = Util.formatDoubleThousandSeparatorWithMoneySign(it)
        }
        metricsData.market_data?.real_volume_last_24_hours?.let {
            binding.tvVolume.text = Util.formatDoubleThousandSeparatorWithMoneySign(it)
        }
        metricsData.marketcap?.marketcap_dominance_percent?.let {
            binding.tvMarketDominance.text = Util.formatDoubleWithPercentSign(it)
        }
        metricsData.all_time_high?.price?.let {
            binding.tvAllTimeHigh.text =
                Util.formatDoubleThousandSeparatorAndPrecisionWithMoneySign(it)
        }

        metricsData.market_data?.percent_change_usd_last_24_hours?.let {
            if (it > 0) {
                binding.tvCryptoPercentChange24Hours.text =
                    ("+" + Util.formatDoubleWithPercentSign(it))

                binding.cvCryptoPercentChange24Hours.setCardBackgroundColor(
                    binding.root.context.getColor(
                        R.color.green_alpha
                    )
                )
                binding.tvCryptoPercentChange24Hours.setTextColor(
                    binding.root.context.getColor(
                        R.color.green
                    )
                )
            } else if (it < 0) {
                binding.tvCryptoPercentChange24Hours.text =
                    (Util.formatDoubleWithPercentSign(it))

                binding.cvCryptoPercentChange24Hours.setCardBackgroundColor(
                    binding.root.context.getColor(
                        R.color.red_alpha
                    )
                )
                binding.tvCryptoPercentChange24Hours.setTextColor(
                    binding.root.context.getColor(
                        R.color.red
                    )
                )
            } else {
                binding.tvCryptoPercentChange24Hours.text =
                    (Util.formatDoubleWithPercentSign(it))

                binding.cvCryptoPercentChange24Hours.setCardBackgroundColor(
                    binding.root.context.getColor(
                        R.color.grey_alpha
                    )
                )
                binding.tvCryptoPercentChange24Hours.setTextColor(
                    binding.root.context.getColor(
                        R.color.grey
                    )
                )
            }
        }

        Glide.with(binding.root)
            .setDefaultRequestOptions(requestOptions)
            .load(
                "https://coinicons-api.vercel.app/api/icon/" + (metricsData.symbol?.lowercase()
                    ?: "btc")
            )
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.ivCryptoIcon)


        //For showing shimmer min 1 times
        time = System.currentTimeMillis() - time
        if (time < timeForShimmer) {
            GlobalScope.launch(context = Dispatchers.Main) {
                delay(time)
                binding.rlCryptoDetailShimmer.visibility = View.GONE
                binding.rlCryptoIconAndTopData.visibility = View.VISIBLE
            }
        } else {
            binding.rlCryptoDetailShimmer.visibility = View.GONE
            binding.rlCryptoIconAndTopData.visibility = View.VISIBLE
        }
    }


}