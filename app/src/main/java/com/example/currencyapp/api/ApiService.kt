package com.example.currencyapp.api

import com.example.currencyapp.model.convert.ConvertResponse
import com.example.currencyapp.model.sympols.SympolsResponse
import retrofit2.Response
import retrofit2.http.*


interface ApiService {
    @GET("symbols")
    suspend fun getsympols(@Header("apikey")apikey:String)
    : Response<SympolsResponse>
    @GET("convert")
    suspend fun convertdata(@Header("apikey")apikey:String,@Query("amount") amount: String,@Query("from") from: String,@Query("to") to: String)
            : Response<ConvertResponse>

}