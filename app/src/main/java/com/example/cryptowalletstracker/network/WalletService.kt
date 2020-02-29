package com.example.cryptowalletstracker.network

import com.example.cryptowalletstracker.model.Balance
import com.example.cryptowalletstracker.util.Constants
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface WalletService {

    @Headers("Content-Type:application/json", "x-api-key:${Constants.API_KEY}")
    @GET("bc/{coin}/mainnet/address/{address}")
    suspend fun getBalance(@Path("coin") coin: String, @Path("address") address: String): Balance

}