package es.webandroid.marvel.domain.entities

interface Type {
    val value: String
}

enum class PortraitType(override val value: String) : Type {
    SMALL("portrait_small"),
    MEDIUM("portrait_medium"),
    XLARGE("portrait_xlarge"),
    FANTASTIC("portrait_fantastic"),
    UNCANNY("portrait_uncanny"),
    INCREDIBLE("portrait_incredible")
}

enum class LandscapeType(override val value: String) : Type {
    SMALL("landscape_small"),
    MEDIUM("landscape_medium"),
    LARGE("landscape_large"),
    XLARGE("landscape_xlarge"),
    AMAZING("landscape_amazing"),
    INCREDIBLE("landscape_incredible")
}
