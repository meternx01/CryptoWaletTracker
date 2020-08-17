package com.example.cryptowalletstracker.model

import com.squareup.moshi.Json

data class Payload(
    @field:Json(name = "address")
    var address: String,
    @field:Json(name = "totalSpent")
    var totalSpent: String,
    @field:Json(name = "totalReceived")
    var totalReceived: String,
    @field:Json(name = "balance")
    var balance: String
)