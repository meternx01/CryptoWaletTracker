package com.example.cryptowalletstracker.repository

import com.example.cryptowalletstracker.db.entities.Wallet
import com.example.cryptowalletstracker.model.Balance

interface Repository {
    suspend fun insertWallet(wallet: Wallet): Long

    suspend fun retrieveBalance(coin: String, address: String): Balance
}