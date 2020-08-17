package com.example.cryptowalletstracker.viewmodel.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cryptowalletstracker.viewmodel.WalletViewModel

class WalletViewModelFactory(private val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WalletViewModel(app) as T
    }
}