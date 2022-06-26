package io.github.alemazzo.sushime.model.database.dishes_in_orders

import androidx.room.Entity

@Entity(tableName = "dishes_in_orders", primaryKeys = ["dishId", "orderId"])
data class DishInOrder(
    val dishId: Int,
    val orderId: Int,
    val amount: Int,
)
