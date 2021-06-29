package es.webandroid.marvel.framework.local.base

import androidx.room.*
import es.webandroid.marvel.framework.local.model.HeroModel

@Dao
interface HeroDao {

    @Query("SELECT * FROM HeroModel")
    fun getAll(): List<HeroModel>

    @Query("SELECT * FROM HeroModel WHERE id = :id")
    fun findById(id: Long): HeroModel

    @Query("SELECT COUNT(id) FROM HeroModel")
    fun heroCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHeroes(venues: List<HeroModel>)

    @Update
    fun updateHero(venue: HeroModel)

    @Query("DELETE FROM HeroModel")
    fun clearData()
}
