package com.example.currencyapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyapp.model.ErrorModelX
import com.example.currencyapp.model.convert.ConvertResponse
import com.example.currencyapp.repository.HomeRepository
import com.example.currencyapp.utils.NetworkHelper
import com.google.gson.GsonBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import java.net.SocketTimeoutException
import javax.inject.Inject


@HiltViewModel
class ConvertCurrencyViewModel @Inject constructor(
    private val networkHelper: NetworkHelper,
    private val homeRepository: HomeRepository,

    ) :  ViewModel() {
    val convertcurrencylivedata = MutableLiveData<ConvertResponse>()
    val loading = MutableLiveData<Boolean>()
    val msg= MutableLiveData<String>()



    fun getconvertedvalue(apikey:String,amount:String,from:String,to:String)=viewModelScope.launch{

       try{
           loading.postValue(true)
        homeRepository.convert(apikey,amount,from,to).collect {
           if (it.code() == 200) {
               if(it.body()?.success==true){
                   convertcurrencylivedata.value=it.body()

               }else{
                   msg.value=it.body()?.error?.info!!
               }




                }

           else if (it.code() == 401) {
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
