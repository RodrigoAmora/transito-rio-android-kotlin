package br.com.rodrigoamora.transitorio.di

import br.com.rodrigoamora.transitorio.network.AppRetrofit
import org.koin.core.context.GlobalContext.loadKoinModules
import org.koin.dsl.module

fun injectFeature() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(
        listOf(
            retrofitModule,
            servicesModule,
        )
    )
}

val retrofitModule = module {
    single { AppRetrofit("").instantiateRetrofit() }
}

val servicesModule = module {
    single { AppRetrofit("").onibusService() }
}