package br.com.rodrigoamora.transitorio.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Onibus (
    @SerializedName("ordem")
    var ordem: String,

    @SerializedName("latitude")
    var latitude: String,

    @SerializedName("longitude")
    var longitude: String,

    @SerializedName("linha")
    var linha: String,

    @SerializedName("datahoraenvio")
    var datahoraenvio: String
): Serializable
