package es.webandroid.marvel.framework.local.mapper

import es.webandroid.marvel.data.model.HeroDataModel
import es.webandroid.marvel.framework.local.model.HeroModel

fun HeroDataModel.toRoomHero(): HeroModel =
    HeroModel(
        id,
        name,
        description,
        path,
        extension
    )

fun HeroModel.toDataHero(): HeroDataModel =
    HeroDataModel(
        id,
        name,
        description,
        path,
        extension
    )
