package io.github.alemazzo.sushime.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import io.github.alemazzo.sushime.model.database.categories.CategoriesDao
import io.github.alemazzo.sushime.model.database.categories.Category
import io.github.alemazzo.sushime.model.database.dishes.Dish
import io.github.alemazzo.sushime.model.database.dishes.DishesDao
import io.github.alemazzo.sushime.model.database.dishes_in_orders.DishInOrder
import io.github.alemazzo.sushime.model.database.dishes_in_orders.DishesInOrdersDao
import io.github.alemazzo.sushime.model.database.orders.Order
import io.github.alemazzo.sushime.model.database.orders.OrdersDao
import io.github.alemazzo.sushime.model.database.restaurants.Restaurant
import io.github.alemazzo.sushime.model.database.restaurants.RestaurantsDao


@Database(
    entities = [
        Restaurant::class,
        Category::class,
        Dish::class,
        DishInOrder::class,
        Order::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun restaurantsDao(): RestaurantsDao
    abstract fun categoriesDao(): CategoriesDao
    abstract fun dishesDao(): DishesDao
    abstract fun dishesInOrdersDao(): DishesInOrdersDao
    abstract fun ordersDao(): OrdersDao

    companion object {
        private const val DATABASE_NAME = "sushi-me.db"
        private var instance: AppDatabase? = null

        private fun initialize(context: Context) {
            synchronized(this) {
                instance = databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                    .createFromAsset("sushi-me.db")
                    .build()
            }
        }

        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                initialize(context)
            }
            return instance!!
        }
    }
}
