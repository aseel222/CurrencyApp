package com.example.currencyapp.model.sympols


import com.google.gson.annotations.SerializedName

data class SympolsResponse(
    @SerializedName("success")
    val success: Boolean?,
    @SerializedName("symbols")
    val symbols: Symbols?
)