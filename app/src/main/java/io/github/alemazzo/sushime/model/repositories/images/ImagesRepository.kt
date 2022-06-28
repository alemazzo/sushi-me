package io.github.alemazzo.sushime.model.repositories.images

import io.github.alemazzo.sushime.model.database.dishes.Dish
import io.github.alemazzo.sushime.model.database.restaurants.Restaurant

class ImagesRepository {

    fun getRestaurantImageLink(restaurant: Restaurant): String =
        "https://raw.githubusercontent.com/zucchero-sintattico/sushi-me/main/db/restaurant-img/${restaurant.id}.jpg"

    fun getDishImageLink(dish: Dish): String =
        "https://raw.githubusercontent.com/zucchero-sintattico/sushi-me/main/db/img/${dish.id}.jpg"
}
