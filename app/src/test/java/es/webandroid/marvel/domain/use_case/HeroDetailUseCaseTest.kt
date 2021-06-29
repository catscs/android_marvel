package es.webandroid.marvel.domain.use_case

import es.webandroid.marvel.core.functional.Either
import es.webandroid.marvel.core.functional.getOrElse
import es.webandroid.marvel.domain.entities.Hero
import es.webandroid.marvel.domain.repository.Repository
import es.webandroid.marvel.domain.use_cases.HeroDetailUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class HeroDetailUseCaseTest {

    private val repository = mockk<Repository>()

    private lateinit var sut: HeroDetailUseCase

    @Before
    fun setup() {
        sut = HeroDetailUseCase(repository)
    }

    @Test
    fun `when hasInternet is true then get from repository data`() {
        // Prepare
        val heroList = Hero(12, "name", "description", "urlPortrait", "urlLandsCape")
        val params = HeroDetailUseCase.Params(0, true)
        coEvery { repository.getHeroDetail(params.heroId, params.hasInternet) } returns Either.Right(heroList)
        // Result
        val result = runBlocking { sut.run(params) }
        // Check
        coVerify { repository.getHeroDetail(any(), any()) }
        Assert.assertEquals(heroList, result.getOrElse(null))
    }

    @Test
    fun `when hasInternet is false then get from repository data`() {
        // Prepare
        val heroList = Hero(12, "name", "description", "urlPortrait", "urlLandsCape")
        val params = HeroDetailUseCase.Params(0, false)
        coEvery { repository.getHeroDetail(params.heroId, params.hasInternet) } returns Either.Right(heroList)
        // Result
        val result = runBlocking { sut.run(params) }
        // Check
        coVerify { repository.getHeroDetail(any(), any()) }
        Assert.assertEquals(heroList, result.getOrElse(null))
    }
}
