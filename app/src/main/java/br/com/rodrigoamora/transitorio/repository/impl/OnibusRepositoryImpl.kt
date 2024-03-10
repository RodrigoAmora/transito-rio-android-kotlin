package br.com.rodrigoamora.transitorio.repository.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import br.com.rodrigoamora.transitorio.model.Onibus
import br.com.rodrigoamora.transitorio.network.webclient.OnibusWebClient
import br.com.rodrigoamora.transitorio.repository.OnibusRepository
import br.com.rodrigoamora.transitorio.repository.Resource

class OnibusRepositoryImpl(
    private val onibusWebClient: OnibusWebClient
): OnibusRepository {

    private val mediator = MediatorLiveData<Resource<List<Onibus>?>>()

    override fun buscarOnibus(dataInicial: String): LiveData<Resource<List<Onibus>?>> {
        onibusWebClient.buscarOnibus(dataInicial,
            completion = {
            },
            failure = {
            }
        )

        return mediator
    }

}
