package io.github.alemazzo.sushime.model.repositories.restaurants

import io.github.alemazzo.sushime.model.database.AppDatabase

class RestaurantsRepository(private val database: AppDatabase) {

    private val restaurantsDao = database.restaurantsDao()

    val getAll = restaurantsDao::getAll
    val getById = restaurantsDao::getById
    val getByName = restaurantsDao::getByName
    val insert = restaurantsDao::insert
    val update = restaurantsDao::update
    val delete = restaurantsDao::delete

}
