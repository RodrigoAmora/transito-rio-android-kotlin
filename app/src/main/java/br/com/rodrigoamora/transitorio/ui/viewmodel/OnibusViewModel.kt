package br.com.rodrigoamora.transitorio.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.rodrigoamora.transitorio.model.Onibus
import br.com.rodrigoamora.transitorio.repository.OnibusRepository
import br.com.rodrigoamora.transitorio.repository.Resource

class OnibusViewModel(
    private val onibusRepository: OnibusRepository
): ViewModel() {
    fun buscarOnibus(dataInicial: String): LiveData<Resource<List<Onibus>?>> {
        return onibusRepository.buscarOnibus(dataInicial)
    }
}
