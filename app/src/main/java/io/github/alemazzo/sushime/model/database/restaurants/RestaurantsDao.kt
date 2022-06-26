package io.github.alemazzo.sushime.model.database.restaurants

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
abstract class RestaurantsDao {

    @Query("SELECT * FROM restaurants")
    abstract fun getAll(): LiveData<List<Restaurant>>

    @Query("SELECT * FROM restaurants WHERE id = :id")
    abstract fun getById(id: Int): LiveData<Restaurant>

    @Query("SELECT * FROM restaurants WHERE name = :name")
    abstract fun getByName(name: String): LiveData<Restaurant>

    @Insert
    abstract fun insert(vararg ristorante: Restaurant)

    @Update
    abstract fun update(ristorante: Restaurant)

    @Delete
    abstract fun delete(ristorante: Restaurant)

}
