package com.example.cryptowalletstracker.network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitBuilder {

    fun getRetrofit(baseURL: String): WalletService
    {
        return Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(WalletService::class.java)
    }
}