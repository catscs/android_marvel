package es.webandroid.marvel.domain.entities

data class Hero(
    val id: Long,
    val name: String,
    val description: String,
    val path: String,
    val extension: String,
) {
    fun getPhoto(type: Type) = "$path/${type.value}.$extension"
}


