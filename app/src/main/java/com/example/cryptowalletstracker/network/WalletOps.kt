package com.example.cryptowalletstracker.network

import com.example.cryptowalletstracker.model.Balance
import timber.log.Timber

class WalletOps {

    suspend fun retrieveBalance(): Balance {
        val retrofit = RetrofitBuilder().getRetrofit("https://api.cryptoapis.io/v1/")
        val walletResult = retrofit.getBalance("doge", "DPdHJchjuYNxvEi2vhv2XLtKRzNKADq3zc")
        walletResult.coin = "doge"
        Timber.d("retrieveBalance: $walletResult")
        return walletResult
    }
}