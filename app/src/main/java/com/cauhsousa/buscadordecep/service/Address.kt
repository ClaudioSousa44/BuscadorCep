package com.cauhsousa.buscadordecep.service

import com.cauhsousa.buscadordecep.model.AddressList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Address {
    @GET("{cep}/json")
    fun getCep(@Path("cep") cep:String): Call<AddressList>
}