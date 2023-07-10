package com.cauhsousa.buscadordecep.model

data class AddressList(
    val cep: String,
    val logradouro: String,
    val bairro: String,
    val localidade: String,
    val uf: String
)
