package io.github.alemazzo.sushime.model.repositories.categories

import io.github.alemazzo.sushime.model.database.AppDatabase

class CategoriesRepository(private val database: AppDatabase) {

    private val categoriesDao = database.categoriesDao()

    val getAll = categoriesDao::getAll
    val getById = categoriesDao::getById
    val getByName = categoriesDao::getByName
    val insert = categoriesDao::insert
    val update = categoriesDao::update
    val delete = categoriesDao::delete
    val getAllCategoriesWithDishes = categoriesDao::getCategoriesWithDishes

}
