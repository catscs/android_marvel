package es.webandroid.marvel.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.webandroid.marvel.core.connectivity.base.ConnectivityProvider
import es.webandroid.marvel.core.platform.BaseViewModel
import es.webandroid.marvel.domain.entities.Hero
import es.webandroid.marvel.domain.use_cases.HeroDetailUseCase
import javax.inject.Inject

@HiltViewModel
class HeroDetailViewModel @Inject constructor(
    private val connectivityProvider: ConnectivityProvider,
    private val heroDetailUseCase: HeroDetailUseCase
) : BaseViewModel() {

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event>
        get() = _event

    fun getHeroDetail(heroId: Long) {
        if (heroId <= 0) return
        _event.value = Event.Loading
        val hasInternet = connectivityProvider.isNetworkAvailable()
        heroDetailUseCase(HeroDetailUseCase.Params(heroId, hasInternet), viewModelScope) { it.fold(::handlerFailure, ::handleResult) }
    }

    private fun handleResult(hero: Hero) {
        _event.value = Event.Content(hero)
    }

    sealed class Event {
        object Loading : Event()
        data class Content(val hero: Hero) : Event()
    }
}
