package com.gdagtekin.cryptocurrencyapp.ui.cryptolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.gdagtekin.cryptocurrencyapp.R
import com.gdagtekin.cryptocurrencyapp.databinding.ItemCryptoBinding
import com.gdagtekin.cryptocurrencyapp.domain.models.cryptolist.Data
import com.gdagtekin.cryptocurrencyapp.util.Util

class CryptoAdapter(
    private val interaction: Interaction? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val requestOptions = RequestOptions
        .placeholderOf(R.drawable.ic_currencies)
        .error(R.drawable.ic_currencies)


    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Data>() {

        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }

    }
    private val differ =
        AsyncListDiffer(
            CryptoRecyclerChangeCallback(this),
            AsyncDifferConfig.Builder(DIFF_CALLBACK).build()
        )


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CryptoListViewHolder(
            ItemCryptoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            requestOptions = requestOptions,
            interaction = interaction
        )
    }

    internal inner class CryptoRecyclerChangeCallback(
        private val adapter: CryptoAdapter
    ) : ListUpdateCallback {

        override fun onChanged(position: Int, count: Int, payload: Any?) {
            adapter.notifyItemRangeChanged(position, count, payload)
        }

        override fun onInserted(position: Int, count: Int) {
            adapter.notifyItemRangeChanged(position, count)
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            adapter.notifyDataSetChanged()
        }

        override fun onRemoved(position: Int, count: Int) {
            adapter.notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CryptoListViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    var cryptoItems: List<Data>?
        get() = differ.currentList
        set(value) = differ.submitList(value)

    fun clearList() {
        differ.submitList(listOf())
    }

    class CryptoListViewHolder
    constructor(
        private val binding: ItemCryptoBinding,
        private val requestOptions: RequestOptions,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Data) {
            binding.root.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            Glide.with(binding.root)
                .setDefaultRequestOptions(requestOptions)
                .load("https://coinicons-api.vercel.app/api/icon/" + item.symbol.lowercase())
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.ivCryptoIcon)

            binding.tvCryptoName.text = item.name
            binding.tvCryptoSymbol.text = item.symbol
            binding.tvCryptoPrice.text =
                (Util.formatDoubleThousandSeparatorAndPrecisionWithMoneySign(item.metrics.market_data.price_usd))

            if (item.metrics.market_data.percent_change_usd_last_24_hours > 0) {
                binding.tvCryptoPercentChange24Hours.text =
                    ("+" + Util.formatDoubleWithPercentSign(item.metrics.market_data.percent_change_usd_last_24_hours))

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
            } else if (item.metrics.market_data.percent_change_usd_last_24_hours < 0) {
                binding.tvCryptoPercentChange24Hours.text =
                    (Util.formatDoubleWithPercentSign(item.metrics.market_data.percent_change_usd_last_24_hours))

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
                    (Util.formatDoubleWithPercentSign(item.metrics.market_data.percent_change_usd_last_24_hours))

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
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Data)
    }
}
