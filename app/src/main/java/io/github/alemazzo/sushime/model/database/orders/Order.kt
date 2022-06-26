package io.github.alemazzo.sushime.model.database.orders

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val timestamp: Long,
    val restaurantId: Int,
)
