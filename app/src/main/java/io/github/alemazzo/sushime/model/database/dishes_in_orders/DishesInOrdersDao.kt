package io.github.alemazzo.sushime.model.database.dishes_in_orders

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
abstract class DishesInOrdersDao {

    @Query("SELECT * FROM dishes_in_orders")
    abstract fun getAll(): LiveData<List<DishInOrder>>

    @Query("SELECT * FROM dishes_in_orders WHERE dishId = :dishId")
    abstract fun getByDishId(dishId: Int): LiveData<List<DishInOrder>>

    @Query("SELECT * FROM dishes_in_orders WHERE orderId = :orderId")
    abstract fun getByOrderId(orderId: Int): LiveData<List<DishInOrder>>

    @Insert
    abstract fun insert(dishInOrder: DishInOrder)

    @Update
    abstract fun update(dishInOrder: DishInOrder)

    @Delete
    abstract fun delete(dishInOrder: DishInOrder)

}
