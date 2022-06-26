package io.github.alemazzo.sushime.model.database.categories

import androidx.lifecycle.LiveData
import androidx.room.*
import io.github.alemazzo.sushime.model.database.dishes.CategoryWithDishes

@Dao
abstract class CategoriesDao {

    @Query("SELECT * FROM categories")
    abstract fun getAll(): LiveData<List<Category>>

    @Query("SELECT * FROM categories WHERE id = :id")
    abstract fun getById(id: Int): LiveData<Category>

    @Query("SELECT * FROM categories WHERE name = :name")
    abstract fun getByName(name: String): LiveData<Category>

    @Transaction
    @Query("SELECT * FROM categories")
    abstract fun getCategoriesWithDishes(): LiveData<List<CategoryWithDishes>>

    @Insert
    abstract fun insert(vararg categorie: Category)

    @Update
    abstract fun update(categoria: Category)

    @Delete
    abstract fun delete(categoria: Category)

}
