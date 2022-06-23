package io.github.alemazzo.sushime.model.repositories.dishes

import io.github.alemazzo.sushime.model.database.AppDatabase

class DishesRepository(private val database: AppDatabase) {

    private val dishesDao = database.dishesDao()

    val getAll = dishesDao::getAll
    val getById = dishesDao::getById
    val getByName = dishesDao::getByName
    val insert = dishesDao::insert
    val update = dishesDao::update
    val delete = dishesDao::delete

}
