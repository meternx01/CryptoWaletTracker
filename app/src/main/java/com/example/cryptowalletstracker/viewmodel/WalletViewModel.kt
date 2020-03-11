/*
 * Copyright (c) 2020 by Jason McKinney.
 */

package com.example.cryptowalletstracker.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptowalletstracker.db.WalletDatabase
import com.example.cryptowalletstracker.db.WalletRepository
import com.example.cryptowalletstracker.db.dao.WalletDao
import com.example.cryptowalletstracker.db.entities.Wallet
import com.example.cryptowalletstracker.network.WalletOps
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class WalletViewModel(application: Application) : ViewModel() {

    val state = MutableLiveData<AppState>()

    sealed class AppState {
        object LOADING : AppState()
        data class SUCCESS(val walletArray: List<Wallet>) : AppState()
        data class ERROR(val message: String) : AppState()
    }

    private lateinit var wallets: List<Wallet>
    private var allWallets: List<Wallet>? = null
    private lateinit var repository: WalletRepository
    private lateinit var walletDao: WalletDao


    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                walletDao = WalletDatabase.getInstance(application)!!.walletDao()
                repository = WalletRepository(walletDao)
            }
            wallets = repository.allWallets
        }
    }

    fun addWallet(walletDatabase: WalletDatabase?) {
        state.value = AppState.LOADING
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val payload =
                    WalletOps().retrieveBalance("doge", "DPdHJchjuYNxvEi2vhv2XLtKRzNKADq3zc")
                Timber.d("CoroutineScope: $payload")
                var wallet = Wallet()
                wallet = wallet.getWalletFromBalance(payload)
                repository.insertWallet(wallet)
//                walletDatabase?.walletDao()?.insertWallets(wallet)
                allWallets = repository.allWallets
            }
            wallets = allWallets ?: listOf()
            state.value = AppState.SUCCESS(wallets)
        }
    }

    fun refreshWallets(walletDatabase: WalletDatabase?) {
        state.value = AppState.LOADING
        viewModelScope.launch {
            val walletArray = wallets
            for (i in walletArray.indices) {
                wallets =
                    withContext(Dispatchers.IO) { updateWallet(walletDatabase, walletArray[i]) }
            }
            state.value = AppState.SUCCESS(wallets)
        }
    }


    suspend fun updateWallet(walletDatabase: WalletDatabase?, input: Wallet): List<Wallet> {
        val payload =
            WalletOps().retrieveBalance(input.coin, input.address)
        var wallet = Wallet()
        wallet = wallet.getWalletFromBalance(payload)
        repository.insertWallet(wallet)
        return repository.allWallets
    }

    fun initViewModel(walletDatabase: WalletDatabase?) {
        viewModelScope.launch {
            state.value = AppState.LOADING
            withContext(Dispatchers.IO) {
                allWallets = walletDatabase?.walletDao()?.getAllWallets()
            }
            wallets = allWallets ?: listOf()
            state.value = AppState.SUCCESS(wallets)
        }
    }
}
