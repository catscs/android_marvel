package es.webandroid.marvel.data.repository

import es.webandroid.marvel.core.error_handling.Failure
import es.webandroid.marvel.core.functional.Either
import es.webandroid.marvel.core.functional.getOrElse
import es.webandroid.marvel.data.mapper.HeroMapper
import es.webandroid.marvel.data.model.HeroDataModel
import es.webandroid.marvel.data.source.LocalDataSource
import es.webandroid.marvel.data.source.RemoteDataSource
import es.webandroid.marvel.domain.entities.Hero
import es.webandroid.marvel.domain.repository.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val heroMapper: HeroMapper
) : Repository {

    override suspend fun getHeroes(offset: Int, limit: Int, hasInternet: Boolean): Either<Failure, List<Hero>> {
        if (hasInternet) {
            val response = remoteDataSource.getHeroes(offset, limit)
            localDataSource.clearData()
            localDataSource.saveHeroes(response.getOrElse(emptyList()))
            return handlerResponse(
                data = response.getOrElse(emptyList()),
                transform = { it.map { hero -> heroMapper.mapTo(hero) } },
                default = emptyList()
            )
        }

        return try {
            val heroes = localDataSource.getHeroes()
            Either.Right(heroes.map { heroMapper.mapTo(it) })
        } catch (e: Exception) {
            Either.Left(Failure.DBError)
        }
    }

    override suspend fun getHeroDetail(heroId: Long, hasInternet: Boolean): Either<Failure, Hero> {
        if (hasInternet) {
            val response = remoteDataSource.getHeroDetail(heroId)
            return handlerResponse(
                data = response.getOrElse(HeroDataModel.empty),
                transform = { heroMapper.mapTo(it) },
                default = HeroDataModel.empty
            )
        }

        return try {
            Either.Right(heroMapper.mapTo(localDataSource.findById(heroId)))
        } catch (e: Exception) {
            Either.Left(Failure.DBError)
        }
    }

    private fun <R, D> handlerResponse(data: D?, transform: (D) -> R, default: D) = Either.Right(transform(data ?: default))
}
