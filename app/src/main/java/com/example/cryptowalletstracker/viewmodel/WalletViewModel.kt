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
import retrofit2.HttpException
import timber.log.Timber

class WalletViewModel(application: Application) : ViewModel() {
    var wallets = MutableLiveData<Array<Wallet>>()
    private var allWallets: Array<Wallet>? = null
    private lateinit var repository: WalletRepository
    private lateinit var walletDao: WalletDao
    var status = MutableLiveData<String?>()


    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                walletDao = WalletDatabase.getInstance(application)!!.walletDao()
                repository = WalletRepository(walletDao)
            }
            wallets.value = repository.allWallets
            status.value = null
        }
    }

    fun addWallet(walletDatabase: WalletDatabase?, coin: String, address: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val payload =
                        WalletOps().retrieveBalance(coin, address)
                    //WalletOps()
                    Timber.d("CoroutineScope: $payload")
                    var wallet = Wallet()
                    wallet = wallet.getWalletFromBalance(payload)
                    walletDatabase?.walletDao()?.insertWallets(wallet)
                    allWallets = walletDatabase?.walletDao()?.getAllWallets()
                    status.value = null
                } catch (e: HttpException) {
                    // Toast with error message
                    withContext(Dispatchers.Main) { status.value = "Error: ${e.message()}" }
                } catch (ex: Exception) {
                    // Toast Offline/ Retrofit error
                    withContext(Dispatchers.Main) { status.value = "Check Your Network Connection" }
                }
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
            try {
                val walletArray = repository.allWallets
                for (i in walletArray.indices) {
                    wallets.value =
                        withContext(Dispatchers.IO) {
                            updateWallet(
                                walletDatabase,
                                walletArray[i]
                            )
                        }
                }
            } catch (e: HttpException) {
                // Toast with error message
                status.value = "Error: ${e.message()}"
            } catch (ex: Exception) {
                // Toast Offline/ Retrofit error
                status.value = "Check Your Network Connection"
            }
        }
    }


    private suspend fun updateWallet(
        walletDatabase: WalletDatabase?,
        input: Wallet
    ): Array<Wallet>? {
        val payload =
            WalletOps().retrieveBalance(input.coin, input.address)
        var wallet = Wallet()
        wallet = wallet.getWalletFromBalance(payload)
        walletDatabase?.walletDao()?.insertWallets(wallet)
        return walletDatabase?.walletDao()?.getAllWallets()
    }
}
