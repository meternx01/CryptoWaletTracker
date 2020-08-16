/*
 * Copyright (c) 2020 by Jason McKinney.
 */

package com.example.cryptowalletstracker.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cryptowalletstracker.db.WalletDatabase
import com.example.cryptowalletstracker.db.entities.Wallet
import com.example.cryptowalletstracker.network.WalletOps
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class WalletViewModel(application: Application) : AndroidViewModel(application) {
    var wallets = MutableLiveData<Array<Wallet>>()
    private var allWallets: Array<Wallet>? = null
    private lateinit var walletDatabase: WalletDatabase


    init {
        wallets.value = emptyArray()
    }

    fun addWallet(coin: String, address: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val payload =
                    WalletOps().retrieveBalance(coin, address)
                Timber.d("CoroutineScope: $payload")
                var wallet = Wallet()
                wallet = wallet.getWalletFromBalance(payload)
                walletDatabase.walletDao().insertWallets(wallet)
                allWallets = walletDatabase.walletDao().getAllWallets()
            }
            wallets.value = allWallets
        }
    }

    fun initViewModel() {
        walletDatabase = WalletDatabase.getInstance(getApplication())!!
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                allWallets = walletDatabase.walletDao().getAllWallets()
            }
            wallets.value = allWallets
        }
    }

    fun refreshWallets() {
        viewModelScope.launch {
            val walletArray = wallets.value
            if (walletArray != null)
                for (i in walletArray.indices) {
                    wallets.value =
                        withContext(Dispatchers.IO) { updateWallet(walletArray[i]) }
                }
        }
    }


    suspend fun updateWallet(input: Wallet): Array<Wallet>? {
        val payload =
            WalletOps().retrieveBalance(input.coin, input.address)
        var wallet = Wallet()
        wallet = wallet.getWalletFromBalance(payload)
        walletDatabase.walletDao().insertWallets(wallet)
        return walletDatabase.walletDao().getAllWallets()
    }
}
