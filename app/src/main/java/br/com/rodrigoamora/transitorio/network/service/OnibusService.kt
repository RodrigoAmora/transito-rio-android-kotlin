package br.com.rodrigoamora.transitorio.network.service

import br.com.rodrigoamora.transitorio.model.Onibus
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OnibusService {
    @GET("sppo")
    fun buscarOnibus(@Query("dataInicial") dataInicial: String,
                     @Query("dataFinal") dataFinal: String): Call<MutableList<Onibus>>
}