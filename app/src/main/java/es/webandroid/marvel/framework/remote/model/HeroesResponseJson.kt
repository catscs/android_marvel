package es.webandroid.marvel.framework.remote.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class HeroesResponseJson (val code: Long, val data: HeroesJson)

@JsonClass(generateAdapter = true)
class HeroesJson (val results: List<HeroResponseData>)

