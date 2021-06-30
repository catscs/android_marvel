package es.webandroid.marvel.data.repository

import es.webandroid.marvel.core.functional.Either
import es.webandroid.marvel.core.functional.getOrElse
import es.webandroid.marvel.data.mapper.HeroMapper
import es.webandroid.marvel.data.model.HeroDataModel
import es.webandroid.marvel.data.source.LocalDataSource
import es.webandroid.marvel.data.source.RemoteDataSource
import es.webandroid.marvel.domain.entities.Hero
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RepositoryImplTest {

    private val remoteDataSource = mockk<RemoteDataSource>()
    private val localDataSource = mockk<LocalDataSource>()

    private lateinit var sut: RepositoryImpl

    @Before
    fun setup() {
        sut = RepositoryImpl(remoteDataSource, localDataSource, HeroMapper())
    }

    @Test
    fun `when hasInternet is true then get heroes from remote and save data local`() {
        // Prepare
        val heroList = listOf(HeroDataModel(12, "name", "description", "urlPortrait", "urlLandsCape"))
        coEvery { remoteDataSource.getHeroes(any(), any()) } returns Either.Right(heroList)
        coEvery { localDataSource.clearData() } returns Unit
        coEvery { localDataSource.saveHeroes(heroList) } returns Unit
        // Result
        runBlocking { sut.getHeroes(0, 13, true) }
        // Check
        coVerifyOrder {
            remoteDataSource.getHeroes(any(), any())
            localDataSource.clearData()
            localDataSource.saveHeroes(heroList)
        }
    }

    @Test
    fun `when hasInternet is false then get heroes from local`() {
        // Prepare
        val heroDataList = listOf(HeroDataModel(12, "name", "description", "urlPortrait", "urlLandsCape"))
        val heroList = listOf(Hero(12, "name", "description", "urlPortrait", "urlLandsCape"))
        coEvery { localDataSource.getHeroes() } returns heroDataList
        // Result
        val result = runBlocking { sut.getHeroes(0, 13, false) }
        // Check
        coVerify { localDataSource.getHeroes() }
        verify { remoteDataSource wasNot Called }
        assertEquals(heroList, result.getOrElse(emptyList()))
    }

    @Test
    fun `when error db then get heroes from local`() {
        // Prepare data
        coEvery { localDataSource.getHeroes() }.throws(Exception())
        coEvery { localDataSource.isEmpty() } returns false
        // Result
        val result = runBlocking { sut.getHeroes(0, 13, false) }
        // Check
        assert(result.isLeft)
    }

    @Test
    fun `when hasInternet is true then get hero from remote`() {
        // Prepare
        val hero = Hero(12, "name", "description", "urlPortrait", "urlLandsCape")
        val heroDataModel = HeroDataModel(12, "name", "description", "urlPortrait", "urlLandsCape")
        coEvery { remoteDataSource.getHeroDetail(any()) } returns Either.Right(heroDataModel)
        // Result
        val result = runBlocking { sut.getHeroDetail(12, true) }
        // Check
        coVerify { remoteDataSource.getHeroDetail(any()) }
        assertEquals(hero, result.getOrElse(null))
    }

    @Test
    fun `when hasInternet is false then get hero from local`() {
        // Prepare
        val heroData = HeroDataModel(12, "name", "description", "urlPortrait", "urlLandsCape")
        val hero = Hero(12, "name", "description", "urlPortrait", "urlLandsCape")
        coEvery { localDataSource.findById(any()) } returns heroData
        // Result
        val result = runBlocking { sut.getHeroDetail(12, false) }
        // Check
        coVerify { localDataSource.findById(any()) }
        verify { remoteDataSource wasNot Called }
        assertEquals(hero, result.getOrElse(null))
    }

    @Test
    fun `when error db then get hero from local`() {
        // Prepare data
        coEvery { localDataSource.findById(any()) }.throws(Exception())
        // Result
        val result = runBlocking { sut.getHeroDetail(0, false) }
        // Check
        assert(result.isLeft)
    }
}
