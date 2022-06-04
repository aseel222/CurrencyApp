package com.example.currencyapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.currencyapp.model.ErrorModelX
import com.example.currencyapp.model.sympols.SympolsResponse
import com.example.currencyapp.repository.HomeRepository
import com.example.currencyapp.utils.NetworkHelper
import com.google.gson.GsonBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException
import java.lang.Exception
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject


@HiltViewModel
class SympolsViewModel @Inject constructor(
    private val networkHelper: NetworkHelper,
    private val homeRepository: HomeRepository,

    ) :  ViewModel() {
    val sympolslivedata = MutableLiveData<SympolsResponse>()
    val loading = MutableLiveData<Boolean>()
    val msg= MutableLiveData<String>()



    fun getsympols(apikey:String)=viewModelScope.launch{

       try{
           loading.postValue(true)
        homeRepository.getsymbols(apikey).collect {
           if (it.code() == 200) {
                    sympolslivedata.value = it.body()
                    loading.postValue(false)
                } else if (it.code() == 401) {
                    loading.postValue(false)
                    val gson = GsonBuilder().create()
                    val pojo = gson.fromJson(it.errorBody()!!.string(), ErrorModelX::class.java)
                    msg.value = pojo.message!!
                                }

           else {
                    onError(it.message())
                }
            }
        }catch (ex:Exception){

           if(ex is SocketTimeoutException){
               msg.postValue("يتعذر الاتصال بالانترنت")
               loading.postValue(false)
           }else if(ex is IOException){
               msg.postValue("لا يوجد اتصال بالانترنت")
               loading.postValue(false)}
           else if(ex is HttpException){
               msg.postValue("some thing went wrong")
               loading.postValue(false)}
       }
    }

    fun onError(messege:String){
        msg.postValue(messege)
        loading.postValue(false)

    }




}
