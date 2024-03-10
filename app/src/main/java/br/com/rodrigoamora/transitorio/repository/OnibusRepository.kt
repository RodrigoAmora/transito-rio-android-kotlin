package br.com.rodrigoamora.transitorio.repository

import androidx.lifecycle.LiveData
import br.com.rodrigoamora.transitorio.model.Onibus
import retrofit2.http.Query

interface OnibusRepository {
    fun buscarOnibus(@Query("dataInicial") dataInicial: String): LiveData<Resource<List<Onibus>?>>
}
