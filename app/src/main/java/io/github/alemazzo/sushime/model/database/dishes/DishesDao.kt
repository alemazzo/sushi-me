package io.github.alemazzo.sushime.model.database.dishes

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
abstract class DishesDao {

    @Query("SELECT * FROM dishes")
    abstract fun getAll(): LiveData<List<Dish>>

    @Query("SELECT * FROM dishes WHERE id = :id")
    abstract fun getById(id: Int): LiveData<Dish>

    @Query("SELECT * FROM dishes WHERE name = :name")
    abstract fun getByName(name: String): LiveData<Dish>

    @Insert
    abstract fun insert(vararg piatti: Dish)

    @Update
    abstract fun update(piatto: Dish)

    @Delete
    abstract fun delete(piatto: Dish)

}
