/*
 * Copyright (c) 2020 by Jason McKinney.
 */

package com.example.cryptowalletstracker.network

import com.example.cryptowalletstracker.model.Balance
import timber.log.Timber

class WalletOps {

    suspend fun retrieveBalance(coin: String, address: String): Balance {
        val retrofit = RetrofitBuilder().getRetrofit("https://api.cryptoapis.io/v1/")
        val walletResult = retrofit.getBalance(coin, address)
        walletResult.coin = coin
        Timber.d("retrieveBalance: $walletResult")
        return walletResult
    }
}