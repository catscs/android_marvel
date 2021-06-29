package es.webandroid.marvel.framework.local

import es.webandroid.marvel.data.model.HeroDataModel
import es.webandroid.marvel.data.source.LocalDataSource
import es.webandroid.marvel.framework.local.base.HeroDatabase
import es.webandroid.marvel.framework.local.model.toDataHero
import es.webandroid.marvel.framework.local.model.toRoomHero
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoomDataSource @Inject constructor(
    private val heroDB: HeroDatabase
) : LocalDataSource {

    private val heroDao = heroDB.heroDao()

    override suspend fun isEmpty() = withContext(Dispatchers.IO) { heroDao.heroCount() <= 0 }

    override suspend fun saveHeroes(heroes: List<HeroDataModel>) = withContext(Dispatchers.IO) { heroDao.insertHeroes(heroes.map { it.toRoomHero() }) }

    override suspend fun getHeroes() = withContext(Dispatchers.IO) { heroDao.getAll().map { it.toDataHero() } }

    override suspend fun findById(heroId: Long) = withContext(Dispatchers.IO) { heroDao.findById(heroId).toDataHero() }

    override suspend fun clearData() = withContext(Dispatchers.IO) { heroDao.clearData() }

}
