package com.example.cryptowalletstracker.model

import com.squareup.moshi.Json


data class Balance(
    @field:Json(name = "payload")
    var payload: Payload,
    @field:Json(name = "coin")
    var coin: String
)



