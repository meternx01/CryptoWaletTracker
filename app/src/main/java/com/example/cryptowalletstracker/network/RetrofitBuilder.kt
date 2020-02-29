package com.example.cryptowalletstracker.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder {

    fun getRetrofit(baseURL: String): WalletService
    {
        return Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(WalletService::class.java)
    }
}