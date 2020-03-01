/*
 * Copyright (c) 2020 by Jason McKinney.
 */

package com.example.cryptowalletstracker.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cryptowalletstracker.db.WalletDatabase
import com.example.cryptowalletstracker.db.entities.Wallet
import com.example.cryptowalletstracker.network.WalletOps
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class WalletViewModel : ViewModel() {
    var wallets = MutableLiveData<Array<Wallet>>()


    init {
        wallets.value = emptyArray()
    }

    fun addWallet(walletDatabase: WalletDatabase?) {
        CoroutineScope(Dispatchers.IO).launch {
            Timber.d("CoroutineScope: entered")
            val payload = WalletOps().retrieveBalance("doge", "DPdHJchjuYNxvEi2vhv2XLtKRzNKADq3zc")
            Timber.d("CoroutineScope: $payload")
            var wallet = Wallet()
            wallet = wallet.getWalletFromBalance(payload)
            walletDatabase?.walletDao()?.insertWallets(wallet)
            val allWallets = walletDatabase?.walletDao()?.getAllWallets()
            withContext(Dispatchers.Main) {
                wallets.value = allWallets
            }
        }
    }

    fun initViewModel(walletDatabase: WalletDatabase?) {
        CoroutineScope(Dispatchers.IO).launch {
            val allWallets = walletDatabase?.walletDao()?.getAllWallets()
            withContext(Dispatchers.Main) {
                wallets.value = allWallets
            }
        }

    }
}