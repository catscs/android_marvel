package es.webandroid.marvel.framework.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HeroModel(
    @PrimaryKey
    val id: Long,
    val name: String,
    val description: String,
    val path: String,
    val extension: String,
)
