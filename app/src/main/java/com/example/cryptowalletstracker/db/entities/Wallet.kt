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
    var amount: String,

    @ColumnInfo(name = "spent")
    var spent: String,

    @ColumnInfo(name = "received")
    var recieved: String,

    @ColumnInfo(name = "date")
    var date: String
) {
    constructor() : this("", "", "", "", "", "")

    fun getWalletFromBalance(balance: Balance): Wallet {
        this.address = balance.payload.address
        //this.coin = balance.payload.coin
        this.amount = balance.payload.balance
        this.spent = balance.payload.totalSpent
        this.recieved = balance.payload.totalReceived
//        this.date = getCurrentTimeMillis()
        return this
    }
}