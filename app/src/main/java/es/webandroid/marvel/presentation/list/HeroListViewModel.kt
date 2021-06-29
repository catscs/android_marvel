package es.webandroid.marvel.presentation.list

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.webandroid.marvel.core.connectivity.base.ConnectivityProvider
import es.webandroid.marvel.core.error_handling.Failure
import es.webandroid.marvel.core.platform.BaseViewModel
import es.webandroid.marvel.core.platform.Consumable
import es.webandroid.marvel.domain.entities.Hero
import es.webandroid.marvel.domain.use_cases.HeroUseCase
import javax.inject.Inject

@HiltViewModel
class HeroListViewModel @Inject constructor(
    private val connectivityProvider: ConnectivityProvider,
    private val heroUseCase: HeroUseCase,
) : BaseViewModel() {

    private val _event = MutableLiveData<Consumable<Event>>()
    val event: LiveData<Consumable<Event>>
        get() = _event

    private var listHeroes = mutableListOf<Hero>()

    var listState: Parcelable? = null

    fun getHeroes(offset: Int = 0, limit: Int = BLOCK_SIZE) {
        triggerEvent(Event.Loading)
        val hasInternet = connectivityProvider.isNetworkAvailable()
        heroUseCase(HeroUseCase.Params(offset, limit, hasInternet), viewModelScope) { it.fold(::handlerFailure, ::handlerResult) }
    }

    override fun handlerFailure(failure: Failure) {
        super.handlerFailure(failure)
        triggerEvent(Event.Content(emptyList()))
    }

    private fun handlerResult(heroes: List<Hero>) {
        if (heroes.isNotEmpty()) {
            listHeroes.addAll(heroes)
            triggerEvent(Event.Content(listHeroes))
        }
    }

    fun onHeroClicked(heroId: Long) = triggerEvent(Event.Detail(heroId))

    private fun triggerEvent(event: Event) {
        _event.value = Consumable(event)
    }

    fun reloadLocalData() = triggerEvent(Event.Content(listHeroes))

    sealed class Event {
        object Loading : Event()
        data class Detail(val heroId: Long) : Event()
        data class Content(val heroes: List<Hero>) : Event()
    }
}

