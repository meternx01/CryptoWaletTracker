/*
 * Copyright (c) 2020 by Jason McKinney.
 */

package com.example.cryptowalletstracker.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptowalletstracker.db.WalletDatabase
import com.example.cryptowalletstracker.db.entities.Wallet
import com.example.cryptowalletstracker.network.WalletOps
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class WalletViewModel : ViewModel() {
    var wallets = MutableLiveData<Array<Wallet>>()
    private var allWallets: Array<Wallet>? = null


    init {
        wallets.value = emptyArray()
    }

    fun addWallet(walletDatabase: WalletDatabase?) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val payload =
                    WalletOps().retrieveBalance("doge", "DPdHJchjuYNxvEi2vhv2XLtKRzNKADq3zc")
                Timber.d("CoroutineScope: $payload")
                var wallet = Wallet()
                wallet = wallet.getWalletFromBalance(payload)
                walletDatabase?.walletDao()?.insertWallets(wallet)
                allWallets = walletDatabase?.walletDao()?.getAllWallets()
            }
            wallets.value = allWallets
        }
    }

    fun initViewModel(walletDatabase: WalletDatabase?) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                allWallets = walletDatabase?.walletDao()?.getAllWallets()
            }
            wallets.value = allWallets
        }
    }

    fun refreshWallets(walletDatabase: WalletDatabase?) {
        viewModelScope.launch {
            val walletArray = wallets.value
            if (walletArray != null)
                for (i in walletArray.indices) {
                    wallets.value =
                        withContext(Dispatchers.IO) { updateWallet(walletDatabase, walletArray[i]) }
                }
        }
    }


    suspend fun updateWallet(walletDatabase: WalletDatabase?, input: Wallet): Array<Wallet>? {
        val payload =
            WalletOps().retrieveBalance(input.coin, input.address)
        var wallet = Wallet()
        wallet = wallet.getWalletFromBalance(payload)
        walletDatabase?.walletDao()?.insertWallets(wallet)
        return walletDatabase?.walletDao()?.getAllWallets()
    }
}
