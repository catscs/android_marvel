package es.webandroid.marvel.framework.remote.mapper

import es.webandroid.marvel.data.model.HeroDataModel
import es.webandroid.marvel.framework.remote.model.HeroResponseData


interface MapperTo<in T, out R> {
    fun mapTo(t: T): R
}

class HeroDataMapper : MapperTo<HeroResponseData, HeroDataModel> {
    override fun mapTo(t: HeroResponseData) =
        HeroDataModel(
            t.id,
            t.name,
            t.description,
            t.thumbnail.path,
            t.thumbnail.extension.name.lowercase()
        )
}
