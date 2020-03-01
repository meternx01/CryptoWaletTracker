/*
 * Copyright (c) 2020 by Jason McKinney.
 */

package com.example.cryptowalletstracker.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptowalletstracker.R
import com.example.cryptowalletstracker.db.entities.Wallet
import kotlinx.android.synthetic.main.layout_wallet_card.view.*
import java.text.DecimalFormat

class CustomAdapter(context: Context) : RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {

    var wallets: Array<Wallet> = emptyArray()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private val inflater: LayoutInflater

    init {
        inflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomViewHolder {
        val view = inflater.inflate(R.layout.layout_wallet_card, parent, false)

        return CustomViewHolder(view)
    }


    override fun getItemCount() = wallets.size


    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(wallets[position])
    }

    inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val coinLogo = view.coin_image
        private val coinName = view.coin_name
        private val amount = view.amount_text

        fun bind(wallet: Wallet) {
            when (wallet.coin) {
                "doge" -> {
                    coinLogo.setImageResource(R.drawable.ic_doge)
                    coinLogo.contentDescription = "Dogecoin Icon"
                    coinName.text = coinName.context.getString(R.string.dogecoin)
                }
                "btc" -> {
                    coinLogo.setImageResource(R.drawable.ic_bitcoin)
                    coinLogo.contentDescription = "Bitcoin Icon"
                    coinName.text = coinName.context.getString(R.string.bitcoin)
                }
                else -> {

                }
            }
            val formatter = DecimalFormat("#,##0.################")
            amount.text = formatter.format(wallet.amount)
        }
    }
}