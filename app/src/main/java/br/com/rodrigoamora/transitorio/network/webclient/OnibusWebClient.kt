package br.com.rodrigoamora.transitorio.network.webclient

import br.com.rodrigoamora.transitorio.model.Onibus
import br.com.rodrigoamora.transitorio.network.service.OnibusService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OnibusWebClient(
    private val service: OnibusService
) {
    private fun<T> executeRequest(
        call: Call<T>,
        completion: (salonsSaved: T?) -> Unit,
        failure: (errorCode: Int) -> Unit
    ) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                when (val responseCode = response.code()) {
                    in 200..299 -> {
                        completion(response.body())
                    }
                    else -> {
                        failure(responseCode)
                    }
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                failure(call.hashCode())
            }
        })
    }

    fun buscarOnibus(dataInicial: String,
                     completion: (salonsSaved: List<Onibus>?) -> Unit,
                     failure: (errorCode: Int?) -> Unit
    ) {
        this.executeRequest(
            service.buscarOnibus(dataInicial),
            completion = { salonList -> completion(salonList) },
            failure = { errorCode ->  failure(errorCode) }
        )
    }
}