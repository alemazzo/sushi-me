package io.github.alemazzo.sushime.model.database.categoria

import androidx.lifecycle.LiveData
import androidx.room.*
import io.github.alemazzo.sushime.model.database.piatto.CategoriaConPiatti

@Dao
abstract class CategorieDao {

    @Query("SELECT * FROM categorie")
    abstract fun getAll(): LiveData<List<Categoria>>

    @Query("SELECT * FROM categorie WHERE id = :id")
    abstract fun getById(id: Int): LiveData<Categoria>

    @Query("SELECT * FROM categorie WHERE nome = :nome")
    abstract fun getByName(nome: String): LiveData<Categoria>

    @Transaction
    @Query("SELECT * FROM categorie")
    abstract fun getCategorieConPiatti(): LiveData<List<CategoriaConPiatti>>

    @Insert
    abstract fun insert(vararg categorie: Categoria)

    @Update
    abstract fun update(categoria: Categoria)

    @Delete
    abstract fun delete(categoria: Categoria)

}
