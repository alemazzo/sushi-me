package io.github.alemazzo.sushime.model.database.categories

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
)
