package com.example.currencyapp.model.convert


import com.google.gson.annotations.SerializedName

data class ConvertResponse(
    @SerializedName("date")
    val date: String?,
    @SerializedName("info")
    val info: Info?,
    @SerializedName("query")
    val query: Query?,
    @SerializedName("result")
    val result: Double?,
    @SerializedName("success")
    val success: Boolean?,
    @SerializedName("error")
    val error: Error?
)