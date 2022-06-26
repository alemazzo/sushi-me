package io.github.alemazzo.sushime.model.database.orders

import androidx.room.Embedded
import androidx.room.Relation
import io.github.alemazzo.sushime.model.database.dishes_in_orders.DishInOrder

class OrderWithDishInOrder(
    @Embedded
    val order: Order,
    @Relation(
        parentColumn = "id",
        entityColumn = "dishId"
    )
    val dishesInOrder: List<DishInOrder>,
)
