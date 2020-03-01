/*
 * Copyright (c) 2020 by Jason McKinney.
 */

package com.example.cryptowalletstracker.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cryptowalletstracker.model.Balance

@Entity(tableName = "walletTable")
class Wallet(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "address")
    var address: String,

    @ColumnInfo(name = "coin")
    var coin: String,

    @ColumnInfo(name = "amount")
    var amount: Double,

    @ColumnInfo(name = "spent")
    var spent: String,

    @ColumnInfo(name = "received")
    var received: String,

    @ColumnInfo(name = "date")
    var date: Long
) {
    constructor() : this("", "", 0.0, "", "", 0)

    fun getWalletFromBalance(balance: Balance): Wallet {
        this.address = balance.payload.address
        this.coin = balance.coin
        this.amount = balance.payload.balance.toDouble()
        this.spent = balance.payload.totalSpent
        this.received = balance.payload.totalReceived
        this.date = System.currentTimeMillis()
        return this
    }
}