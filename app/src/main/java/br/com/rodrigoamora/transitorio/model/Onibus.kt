package br.com.rodrigoamora.transitorio.model

import com.google.gson.annotations.SerializedName

data class Onibus (
    @SerializedName("id")
    var ordem: String,

    @SerializedName("latitude")
    var latitude: String,

    @SerializedName("longitude")
    var longitude: String,

    @SerializedName("linha")
    var linha: String,

    @SerializedName("datahoraenvio")
    var datahoraenvio: String
)
