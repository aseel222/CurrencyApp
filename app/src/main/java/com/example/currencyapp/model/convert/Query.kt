package com.example.currencyapp.model.convert


import com.google.gson.annotations.SerializedName

data class Query(
    @SerializedName("amount")
    val amount: Int?,
    @SerializedName("from")
    val from: String?,
    @SerializedName("to")
    val to: String?
)