/*
 * Copyright (c) 2020 by Jason McKinney.
 */

package com.example.cryptowalletstracker.ui

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptowalletstracker.R
import com.example.cryptowalletstracker.db.entities.Wallet
import com.example.cryptowalletstracker.util.DataSources.Companion.COINS
import kotlinx.android.synthetic.main.layout_wallet_card.view.*
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


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
        private val address = view.address
        private val timeUpdated = view.timeUpdated

        fun bind(wallet: Wallet) {

            for (i in 0 until COINS.size) {
                if (wallet.coin == COINS[i].symbol) {
                    coinLogo.setImageResource(COINS[i].image)
                    coinLogo.contentDescription = "${COINS[i].name} Icon"
                    coinName.text = COINS[i].name
                }
            }
            /*when (wallet.coin) {
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
            }*/
            amount.text = DecimalFormat("#,##0.################").format(wallet.amount)
            timeUpdated.text =
                SimpleDateFormat(
                    "MMM dd,yyyy HH:mm",
                    Resources.getSystem().configuration.locales[0]
                ).format(Date(wallet.date))
            address.text = wallet.address

        }
    }
}