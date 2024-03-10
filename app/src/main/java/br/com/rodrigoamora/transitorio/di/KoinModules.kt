package br.com.rodrigoamora.transitorio.di

import br.com.rodrigoamora.transitorio.network.AppRetrofit
import br.com.rodrigoamora.transitorio.network.webclient.OnibusWebClient
import br.com.rodrigoamora.transitorio.ui.viewmodel.OnibusViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.loadKoinModules
import org.koin.dsl.module

fun injectFeature() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(
        listOf(
            retrofitModule,
            servicesModule,
            viewModelModule,
            webClientModule,
        )
    )
}

val retrofitModule = module {
    single { AppRetrofit("").instantiateRetrofit() }
}

val servicesModule = module {
    single { AppRetrofit("").onibusService() }
}

val viewModelModule = module {
    viewModel { OnibusViewModel(get()) }
}

val webClientModule = module {
    single { OnibusWebClient(get()) }
}