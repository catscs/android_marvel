package es.webandroid.marvel.domain.entities

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class HeroTest {

    private lateinit var hero: Hero

    @Before
    fun setup() {
        hero = Hero(12, "name", "description", "http://google.com", "jpg")
    }

    @Test
    fun `be able to check the type of photo portraitType`() {
        // Prepare
        val small = hero.getPhoto(PortraitType.SMALL)
        val medium = hero.getPhoto(PortraitType.MEDIUM)
        val xlarge = hero.getPhoto(PortraitType.XLARGE)
        val fantastic = hero.getPhoto(PortraitType.FANTASTIC)
        val uncanny = hero.getPhoto(PortraitType.UNCANNY)
        val incredible = hero.getPhoto(PortraitType.INCREDIBLE)

        // Check
        Assert.assertEquals("http://google.com/portrait_small.jpg", small)
        Assert.assertEquals("http://google.com/portrait_medium.jpg", medium)
        Assert.assertEquals("http://google.com/portrait_xlarge.jpg", xlarge)
        Assert.assertEquals("http://google.com/portrait_fantastic.jpg", fantastic)
        Assert.assertEquals("http://google.com/portrait_uncanny.jpg", uncanny)
        Assert.assertEquals("http://google.com/portrait_incredible.jpg", incredible)

    }

    @Test
    fun `be able to check the type of photo landscapeType`() {
        // Prepare
        val small = hero.getPhoto(LandscapeType.SMALL)
        val medium = hero.getPhoto(LandscapeType.MEDIUM)
        val large = hero.getPhoto(LandscapeType.LARGE)
        val xlarge = hero.getPhoto(LandscapeType.XLARGE)
        val amazing = hero.getPhoto(LandscapeType.AMAZING)
        val incredible = hero.getPhoto(LandscapeType.INCREDIBLE)

        // Check
        Assert.assertEquals("http://google.com/landscape_small.jpg", small)
        Assert.assertEquals("http://google.com/landscape_medium.jpg", medium)
        Assert.assertEquals("http://google.com/landscape_large.jpg", large)
        Assert.assertEquals("http://google.com/landscape_xlarge.jpg", xlarge)
        Assert.assertEquals("http://google.com/landscape_amazing.jpg", amazing)
        Assert.assertEquals("http://google.com/landscape_incredible.jpg", incredible)

    }
}
