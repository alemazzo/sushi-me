package io.github.alemazzo.sushime.model.database.piatto

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
abstract class PiattiDao {

    @Query("SELECT * FROM piatti")
    abstract fun getAll(): LiveData<List<Piatto>>

    @Query("SELECT * FROM piatti WHERE id = :id")
    abstract fun getById(id: Int): LiveData<Piatto>

    @Query("SELECT * FROM piatti WHERE nome = :nome")
    abstract fun getByName(nome: String): LiveData<Piatto>

    @Insert
    abstract fun insert(vararg piatti: Piatto)

    @Update
    abstract fun update(piatto: Piatto)

    @Delete
    abstract fun delete(piatto: Piatto)

}
