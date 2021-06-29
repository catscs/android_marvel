package es.webandroid.marvel.data.model

data class HeroDataModel(
    val id: Long,
    val name: String,
    val description: String,
    val path: String,
    val extension: String,
) {
    companion object {
        val empty = HeroDataModel(0, "", "","","")
    }
}
