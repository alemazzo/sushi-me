package io.github.alemazzo.sushime.model.database.dishes

import androidx.room.Embedded
import androidx.room.Relation
import io.github.alemazzo.sushime.model.database.categories.Category


data class CategoryWithDishes(
    @Embedded
    val category: Category,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val dishes: List<Dish>,
)
