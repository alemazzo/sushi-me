package io.github.alemazzo.sushime.model.repositories.dishes_in_orders

import io.github.alemazzo.sushime.model.database.AppDatabase

class DishesInOrdersRepository(private val database: AppDatabase) {

    private val dishesInOrdersDao = database.dishesInOrdersDao()

    val getAll = dishesInOrdersDao::getAll
    val getByDishId = dishesInOrdersDao::getByDishId
    val getByOrderId = dishesInOrdersDao::getByOrderId
    val insert = dishesInOrdersDao::insert
    val update = dishesInOrdersDao::update
    val delete = dishesInOrdersDao::delete
}
