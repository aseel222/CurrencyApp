package com.example.currencyapp.repository


import android.graphics.Bitmap
import android.util.Log
import com.example.currencyapp.api.ApiService
import com.example.currencyapp.model.convert.ConvertResponse
import com.example.currencyapp.model.sympols.SympolsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject
import javax.inject.Named

class HomeRepository @Inject constructor(  val apiService: ApiService) {

    suspend fun getsymbols(apikey:String): Flow <Response<SympolsResponse>> {
        return flow {
            emit(apiService.getsympols(apikey))
        }.flowOn(Dispatchers.IO)
    }
    suspend fun convert(apikey:String,amount:String,from:String,to:String): Flow <Response<ConvertResponse>> {
        return flow {
            emit(apiService.convertdata(apikey,amount,from,to))
        }.flowOn(Dispatchers.IO)
    }




}