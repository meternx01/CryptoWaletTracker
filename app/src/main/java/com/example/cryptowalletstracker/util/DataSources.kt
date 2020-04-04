package com.example.cryptowalletstracker.util

import com.example.cryptowalletstracker.R
import com.example.cryptowalletstracker.model.Coins

interface DataSources {
    companion object {
        val COINS =
            listOf(
                Coins(R.drawable.ic_bitcoin, "Bitcoin", "btc"),
                Coins(R.drawable.ic_litecoin, "Litecoin", "ltc"),
                Coins(R.drawable.ic_doge, "Dogecoin", "doge"),
                Coins(R.drawable.ic_dash, "Dash", "dash"),
                Coins(R.drawable.ic_bitcoin_cash, "Bitcoin Cash", "bch"),
                Coins(R.drawable.ic_ethereum, "Ethereum", "eth"),
                Coins(R.drawable.ic_ethereum_classic, "Ethereum Classic", "etc")

            )
    }
}