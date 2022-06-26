package io.github.alemazzo.sushime.model.database.dishes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dishes")
data class Dish(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val categoryId: Int,
    val name: String,
    val description: String,
)
