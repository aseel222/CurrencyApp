package com.example.currencyapp.model.convert


import com.google.gson.annotations.SerializedName

data class Info(
    @SerializedName("rate")
    val rate: Double?,
    @SerializedName("timestamp")
    val timestamp: Int?
)