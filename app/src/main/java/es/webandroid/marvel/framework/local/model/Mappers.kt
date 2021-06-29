package es.webandroid.marvel.framework.local.model

import es.webandroid.marvel.data.model.HeroDataModel

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
