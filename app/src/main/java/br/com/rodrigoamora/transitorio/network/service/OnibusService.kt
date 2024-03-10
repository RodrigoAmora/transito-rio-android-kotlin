package br.com.rodrigoamora.transitorio.network.service

import retrofit2.http.GET
import retrofit2.http.Query

interface OnibusService {
    @GET("gps/sppo")
    fun buscarOnibus(@Query("dataInicial") dataInicial: String)
}