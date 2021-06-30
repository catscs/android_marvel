package es.webandroid.marvel.data.mapper

import es.webandroid.marvel.data.model.HeroDataModel
import es.webandroid.marvel.domain.entities.Hero

interface MapperTo<in T, out R> {
    fun mapTo(t: T): R
}

class HeroMapper : MapperTo<HeroDataModel, Hero> {
    override fun mapTo(t: HeroDataModel) =
        Hero(
            t.id,
            t.name,
            t.description,
            t.path,
            t.extension
        )
}
