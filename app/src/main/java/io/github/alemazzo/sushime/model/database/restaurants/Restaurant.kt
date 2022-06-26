package io.github.alemazzo.sushime.model.database.restaurants

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "restaurants")
data class Restaurant(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val description: String,
)
