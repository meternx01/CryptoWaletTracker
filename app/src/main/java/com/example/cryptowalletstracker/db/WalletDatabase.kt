package com.example.cryptowalletstracker.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cryptowalletstracker.db.dao.WalletDao
import com.example.cryptowalletstracker.db.entities.Wallet


@Database(entities = [Wallet::class], version = 1)
abstract class WalletDatabase : RoomDatabase() {

    abstract fun walletDao(): WalletDao

    companion object {
        private var INSTANCE: WalletDatabase? = null
        fun getInstance(context: Context): WalletDatabase? {
            if (INSTANCE == null) {
                synchronized(WalletDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        WalletDatabase::class.java, "wallet-database"
                    )
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}