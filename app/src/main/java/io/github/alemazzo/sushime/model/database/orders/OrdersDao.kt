package io.github.alemazzo.sushime.model.database.orders

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
abstract class OrdersDao {

    @Query("SELECT * FROM orders")
    abstract fun getAll(): LiveData<List<Order>>

    @Query("SELECT * FROM orders WHERE id = :id")
    abstract fun getById(id: Int): LiveData<Order>

    @Query("SELECT * FROM orders WHERE restaurantId = :restaurantId")
    abstract fun getAllOrdersByRestaurantId(restaurantId: Int): LiveData<List<Order>>

    @Transaction
    @Query("SELECT * FROM orders")
    abstract fun getAllOrdersWithDishInOrder(): LiveData<List<OrderWithDishInOrder>>

    @Insert
    abstract fun insert(order: Order): Long

    @Update
    abstract fun update(order: Order)

    @Delete
    abstract fun delete(order: Order)
}
