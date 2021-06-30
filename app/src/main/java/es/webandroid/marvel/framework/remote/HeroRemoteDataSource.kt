package es.webandroid.marvel.framework.remote

import es.webandroid.marvel.core.error_handling.Failure
import es.webandroid.marvel.core.error_handling.Failure.NetworkError.Fatal
import es.webandroid.marvel.core.error_handling.Failure.NetworkError.Recoverable
import es.webandroid.marvel.core.functional.Either
import es.webandroid.marvel.data.model.HeroDataModel
import es.webandroid.marvel.data.source.RemoteDataSource
import es.webandroid.marvel.framework.remote.mapper.HeroDataMapper
import es.webandroid.marvel.framework.remote.model.HeroResponseData
import kotlinx.coroutines.Dispatchers
import retrofit2.Response
import javax.inject.Inject

class HeroRemoteDataSource @Inject constructor(
    private val networkApi: NetworkApi,
    private val heroDataMapper: HeroDataMapper
) : RemoteDataSource {

    override suspend fun getHeroes(offset: Int, limit: Int): Either<Failure, List<HeroDataModel>> = with(Dispatchers.IO) {
        val response = networkApi.getHeroes(offset, limit)
        handlerResponse(
            response,
            data = response.body()?.data?.results,
            transform = { it.map { hero -> heroDataMapper.mapTo(hero) } },
            default = emptyList()
        )
    }

    override suspend fun getHeroDetail(heroId: Long): Either<Failure, HeroDataModel> = with(Dispatchers.IO) {
        val response = networkApi.getHeroDetail(heroId)
        handlerResponse(
            response,
            data = response.body()?.data?.results?.first(),
            transform = { heroDataMapper.mapTo(it) },
            default = HeroResponseData.empty
        )
    }

    private fun <Json, D, R> handlerResponse(response: Response<Json>, data: D?, transform: (D) -> R, default: D): Either<Failure, R> {
        return when (response.isSuccessful) {
            true -> Either.Right(transform(data ?: default))
            false -> Either.Left(getNetworkError(response.code(), response.message()))
        }
    }

    private fun getNetworkError(statusCode: Int, message: String?): Failure {
        return when (statusCode) {
            in 400..499 -> Fatal(message)
            else -> Recoverable(message)
        }
    }
}
