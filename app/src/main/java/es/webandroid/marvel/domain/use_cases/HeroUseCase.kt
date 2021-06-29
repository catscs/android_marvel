package es.webandroid.marvel.domain.use_cases

import es.webandroid.marvel.domain.entities.Hero
import es.webandroid.marvel.domain.repository.Repository
import es.webandroid.marvel.domain.use_cases.HeroUseCase.Params
import javax.inject.Inject

class HeroUseCase @Inject constructor(private val repository: Repository) : UseCase<List<Hero>, Params>() {

    override suspend fun run(params: Params) = repository.getHeroes(params.offset, params.limit, params.hasInternet)

    data class Params(
        val offset: Int,
        val limit: Int,
        val hasInternet: Boolean
    )
}
