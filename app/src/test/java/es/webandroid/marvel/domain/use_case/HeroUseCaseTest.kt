package es.webandroid.marvel.domain.use_case

import es.webandroid.marvel.core.functional.Either
import es.webandroid.marvel.core.functional.getOrElse
import es.webandroid.marvel.domain.entities.Hero
import es.webandroid.marvel.domain.repository.Repository
import es.webandroid.marvel.domain.use_cases.HeroUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class HeroUseCaseTest {

    private val repository = mockk<Repository>()

    private lateinit var sut: HeroUseCase

    @Before
    fun setup() {
        sut = HeroUseCase(repository)
    }

    @Test
    fun `when hasInternet is true then get from repository data`() {
        // Prepare
        val heroList = listOf(Hero(12, "name", "description", "urlPortrait", "urlLandsCape"))
        val params = HeroUseCase.Params(0, 13, true)
        coEvery { repository.getHeroes(params.offset, params.limit, params.hasInternet) } returns Either.Right(heroList)
        // Result
        val result = runBlocking { sut.run(params) }
        // Check
        coVerify { repository.getHeroes(any(), any(), any()) }
        assertEquals(heroList, result.getOrElse(emptyList()))
    }

    @Test
    fun `when hasInternet is false then get from repository data`() {
        // Prepare
        val heroList = listOf(Hero(12, "name", "description", "urlPortrait", "urlLandsCape"))
        val params = HeroUseCase.Params(0, 13, false)
        coEvery { repository.getHeroes(params.offset, params.limit, params.hasInternet) } returns Either.Right(heroList)
        // Result
        val result = runBlocking { sut.run(params) }
        // Check
        coVerify { repository.getHeroes(any(), any(), any()) }
        assertEquals(heroList, result.getOrElse(emptyList()))
    }
}
