package com.example.cryptowalletstracker.model

import java.util.*

data class Payload(

    var address: String,
    var totalSpent: String,
    var totalReceived: String,
    var balance: String,
    var txi: Int,
    var txo: Int,
    var txsCount: Int,
    var addresses: ArrayList<String>,
    var coin: String = "",
    var time: Date? = null

)