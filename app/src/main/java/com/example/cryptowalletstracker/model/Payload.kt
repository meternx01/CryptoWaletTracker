package com.example.cryptowalletstracker.model

data class Payload(
    var address: String,
    var totalSpent: String,
    var totalReceived: String,
    var balance: String
)