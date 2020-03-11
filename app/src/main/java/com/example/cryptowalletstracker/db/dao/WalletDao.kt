package com.example.cryptowalletstracker.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.cryptowalletstracker.db.entities.Wallet


@Dao
interface WalletDao {
    //"SELECT * FROM walletTable"
    @Query("SELECT * FROM walletTable")
    fun getAllWallets(): List<Wallet>

    @Insert(onConflict = REPLACE)
    fun insertWallets(wallets: Wallet): Long
}