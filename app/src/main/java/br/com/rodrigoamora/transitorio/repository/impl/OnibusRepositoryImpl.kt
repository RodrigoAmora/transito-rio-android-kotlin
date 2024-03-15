package br.com.rodrigoamora.transitorio.repository.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import br.com.rodrigoamora.transitorio.model.Onibus
import br.com.rodrigoamora.transitorio.network.webclient.OnibusWebClient
import br.com.rodrigoamora.transitorio.repository.OnibusRepository
import br.com.rodrigoamora.transitorio.repository.Resource

class OnibusRepositoryImpl(
    private val onibusWebClient: OnibusWebClient
): OnibusRepository {

    private val mediator = MediatorLiveData<Resource<List<Onibus>?>>()

    override fun buscarOnibus(dataInicial: String): LiveData<Resource<List<Onibus>?>> {
        val failuresFromWebApiLiveData = MutableLiveData<Resource<List<Onibus>?>>()

        this.onibusWebClient.buscarOnibus(dataInicial,
            completion = { listaOnibus ->
                mediator.value = Resource(listaOnibus)
            },
            failure = {
            }
        )

        return this.mediator
    }

}
