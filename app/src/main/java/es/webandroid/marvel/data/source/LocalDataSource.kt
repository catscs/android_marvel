package es.webandroid.marvel.data.source

import es.webandroid.marvel.data.model.HeroDataModel

interface LocalDataSource {
    suspend fun getHeroes(): List<HeroDataModel>
    suspend fun isEmpty(): Boolean
    suspend fun saveHeroes(heroes: List<HeroDataModel>)
    suspend fun findById(heroId: Long): HeroDataModel
    suspend fun clearData()
}
