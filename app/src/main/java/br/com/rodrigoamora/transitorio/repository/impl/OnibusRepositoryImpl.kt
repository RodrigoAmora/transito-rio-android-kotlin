package br.com.rodrigoamora.transitorio.repository.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import br.com.rodrigoamora.transitorio.model.Onibus
import br.com.rodrigoamora.transitorio.network.webclient.OnibusWebClient
import br.com.rodrigoamora.transitorio.repository.OnibusRepository
import br.com.rodrigoamora.transitorio.repository.Resource
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.util.TimeZone

class OnibusRepositoryImpl(private val onibusWebClient: OnibusWebClient): OnibusRepository {

    private val mediator = MediatorLiveData<Resource<List<Onibus>?>>()

    override fun buscarOnibus(dataInicial: String,
                              dataFinal: String): LiveData<Resource<List<Onibus>?>> {
        val failuresFromWebApiLiveData = MutableLiveData<Resource<List<Onibus>?>>()

        this.onibusWebClient.buscarOnibus(dataInicial, dataFinal,
            completion = { listaOnibus ->
                listaOnibus?.let {
                    val ultimasPosicoes = this.verificarHora(it)
                    mediator.value = Resource(ultimasPosicoes)
                }
            },
            failure = {}
        )

        return this.mediator
    }

    private fun verificarHora(listaOnibus: List<Onibus>): List<Onibus> {
        val novaLista = mutableListOf<Onibus>()
        for (onibus in listaOnibus) {
            val triggerTime =
                LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(onibus.datahoraenvio.toLong()),
                    TimeZone.getTimeZone("America/Sao_Paulo").toZoneId()
                ).plusHours(1)

            val diferencaEmSegundos = Duration.between(triggerTime, LocalDateTime.now()).seconds
                                                    //.toSeconds()
            if (diferencaEmSegundos <= 2) {
                novaLista.add(onibus)
            }
        }
        return novaLista.toList()
    }
}
