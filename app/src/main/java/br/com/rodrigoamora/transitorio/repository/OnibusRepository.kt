package br.com.rodrigoamora.transitorio.repository

import androidx.lifecycle.LiveData
import br.com.rodrigoamora.transitorio.model.Onibus

interface OnibusRepository {
    fun buscarOnibus(dataInicial: String, dataFinal: String): LiveData<Resource<List<Onibus>?>>
}
