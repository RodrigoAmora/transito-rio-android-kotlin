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

    private val mediator = MediatorLiveData<Resource<MutableList<Onibus>?>>()

    override fun buscarOnibus(dataInicial: String,
                              dataFinal: String): LiveData<Resource<MutableList<Onibus>?>> {
        val failuresFromWebApiLiveData = MutableLiveData<Resource<List<Onibus>?>>()

        this.onibusWebClient.buscarOnibus(dataInicial, dataFinal,
            completion = { listaOnibus ->
                listaOnibus?.let {
                    val ultimasPosicoes = this.removerOnibusRepetidos(it)
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

    private fun removerOnibusRepetidos(listaOnibus: MutableList<Onibus>): MutableList<Onibus> {
        listaOnibus.reverse()

        var onibusAnterior = listaOnibus.get(0)


        val novaLista = mutableListOf<Onibus>()
        novaLista.add(onibusAnterior)

        val listOrdens = mutableListOf<String>()
        listOrdens.add(onibusAnterior.ordem)

        for (i in 1 .. listaOnibus.size-1) {
            val onibus = listaOnibus[i]
            if (onibus.ordem != onibusAnterior.ordem && !listOrdens.contains(onibus.ordem)) {
                novaLista.add(onibus)
                listOrdens.add(onibus.ordem)
            }

            onibusAnterior = onibus
        }
        listOrdens.clear()
        listaOnibus.clear()
        return novaLista
    }
}
