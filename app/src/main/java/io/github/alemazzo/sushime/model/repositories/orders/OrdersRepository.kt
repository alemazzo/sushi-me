package io.github.alemazzo.sushime.model.repositories.orders

import io.github.alemazzo.sushime.model.database.AppDatabase

class OrdersRepository(private val database: AppDatabase) {

    private val ordersDatabase = database.ordersDao()

    val getAll = ordersDatabase::getAll
    val getById = ordersDatabase::getById
    val getAllOrdersByRestaurantId = ordersDatabase::getAllOrdersByRestaurantId
    val getAllOrdersWithDishInOrder = ordersDatabase::getAllOrdersWithDishInOrder
    val getOrderWithDishesInOrderByOrderId = ordersDatabase::getOrderWithDishesInOrderByOrderId
    val insert = ordersDatabase::insert
    val update = ordersDatabase::update
    val delete = ordersDatabase::delete
}
