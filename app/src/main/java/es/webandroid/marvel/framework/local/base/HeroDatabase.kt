package es.webandroid.marvel.framework.local.base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import es.webandroid.marvel.framework.local.model.HeroModel

@Database(entities = [HeroModel::class], version = 1)
abstract class HeroDatabase : RoomDatabase() {

    abstract fun heroDao(): HeroDao

    companion object {
        fun build(context: Context) = Room.databaseBuilder(context, HeroDatabase::class.java, "marvel_db").build()

    }
}
