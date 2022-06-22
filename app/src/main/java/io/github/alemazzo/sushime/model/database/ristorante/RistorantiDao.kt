package io.github.alemazzo.sushime.model.database.ristorante

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
abstract class RistorantiDao {

    @Query("SELECT * FROM ristoranti")
    abstract fun getAll(): LiveData<List<Ristorante>>

    @Query("SELECT * FROM ristoranti WHERE id = :id")
    abstract fun getById(id: Int): LiveData<Ristorante>

    @Query("SELECT * FROM ristoranti WHERE nome = :nome")
    abstract fun getByName(nome: String): LiveData<Ristorante>

    @Insert
    abstract fun insert(vararg ristorante: Ristorante)

    @Update
    abstract fun update(ristorante: Ristorante)

    @Delete
    abstract fun delete(ristorante: Ristorante)

}
