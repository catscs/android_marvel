package es.webandroid.marvel.data.source

import es.webandroid.marvel.core.error_handling.Failure
import es.webandroid.marvel.core.functional.Either
import es.webandroid.marvel.data.model.HeroDataModel
import es.webandroid.marvel.domain.entities.Hero

interface RemoteDataSource {
    suspend fun getHeroes(offset: Int, limit: Int): Either<Failure, List<HeroDataModel>>
    suspend fun getHeroDetail(heroId: Long): Either<Failure, HeroDataModel>
}
