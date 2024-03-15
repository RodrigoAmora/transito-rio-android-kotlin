package br.com.rodrigoamora.transitorio.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.rodrigoamora.transitorio.extension.getDateFormatted
import br.com.rodrigoamora.transitorio.model.Onibus
import br.com.rodrigoamora.transitorio.repository.OnibusRepository
import br.com.rodrigoamora.transitorio.repository.Resource
import java.time.LocalDateTime

class OnibusViewModel(
    private val onibusRepository: OnibusRepository
): ViewModel() {
    fun buscarOnibus(): LiveData<Resource<List<Onibus>?>> {
        val dateTimeFormat = "yyyy-dd-MM:HH:mm:ss"
        return this.onibusRepository.buscarOnibus(LocalDateTime.now().getDateFormatted(dateTimeFormat))
    }
}
