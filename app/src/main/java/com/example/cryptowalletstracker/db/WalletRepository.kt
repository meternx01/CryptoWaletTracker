package com.example.cryptowalletstracker.db

import com.example.cryptowalletstracker.db.dao.WalletDao
import com.example.cryptowalletstracker.db.entities.Wallet

class WalletRepository(private val walletDao: WalletDao) {
    val allWallets: List<Wallet> = walletDao.getAllWallets()

    fun insertWallet(wallet: Wallet) {
        walletDao.insertWallets(wallet)
    }

}