package es.webandroid.marvel.domain.repository

import es.webandroid.marvel.core.error_handling.Failure
import es.webandroid.marvel.core.functional.Either
import es.webandroid.marvel.domain.entities.Hero

interface Repository {
    suspend fun getHeroes(offset: Int, limit: Int, hasInternet: Boolean): Either<Failure, List<Hero>>
    suspend fun getHeroDetail(heroId: Long, hasInternet: Boolean): Either<Failure, Hero>
}
