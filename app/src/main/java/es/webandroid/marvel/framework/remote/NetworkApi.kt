package es.webandroid.marvel.framework.remote

import es.webandroid.marvel.framework.remote.model.HeroesResponseJson
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkApi {

    companion object {
        const val CHARACTERS_LIST = "characters"
        const val CHARACTER = "characters/{id}"
        const val LIMIT = "limit"
        const val OFFSET = "offset"
    }

    @GET(CHARACTERS_LIST)
    suspend fun getHeroes(
        @Query(OFFSET) offset: Int = 0,
        @Query(LIMIT) limit: Int = 13
    ): Response<HeroesResponseJson>

    @GET(CHARACTER)
    suspend fun getHeroDetail(
        @Path("id") id: Long
    ): Response<HeroesResponseJson>
}
