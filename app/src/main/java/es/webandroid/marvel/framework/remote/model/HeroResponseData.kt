package es.webandroid.marvel.framework.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class HeroResponseData(
    val id: Long,
    val name: String,
    val description: String,
    val thumbnail: Thumbnail,
) {
    companion object {
        val empty = HeroResponseData(0, "", "", Thumbnail("", Extension.JPG))
    }
}

@JsonClass(generateAdapter = true)
data class Thumbnail (
    val path: String,
    val extension: Extension
)

enum class Extension {
    @Json(name = "jpg") JPG,
    @Json(name = "png") PNG,
    @Json(name = "gif") GIF
}
