package es.webandroid.marvel.domain.use_cases

import es.webandroid.marvel.core.error_handling.Failure
import es.webandroid.marvel.core.functional.Either
import es.webandroid.marvel.domain.entities.Hero
import es.webandroid.marvel.domain.repository.Repository
import es.webandroid.marvel.domain.use_cases.HeroDetailUseCase.*
import javax.inject.Inject

class HeroDetailUseCase @Inject constructor(
    private val repository: Repository
) : UseCase<Hero, Params>() {

    override suspend fun run(params: Params): Either<Failure, Hero> = repository.getHeroDetail(params.heroId, params.hasInternet)

    data class Params(val heroId: Long, val hasInternet: Boolean)
}
