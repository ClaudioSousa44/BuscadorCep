package com.cauhsousa.buscadordecep.service

import com.cauhsousa.buscadordecep.model.AddressList
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {
    private val BASE_ULR = "https://viacep.com.br/ws/"

    private val retrofitFactory = Retrofit
        .Builder()
        .baseUrl(BASE_ULR)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getCepService(): Address{
        return retrofitFactory.create(Address::class.java)
    }
}