package org.wit.restaurant.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastId = 0L

internal fun getId(): Long {
  return lastId++
}

class RestaurantMemStore : RestaurantStore, AnkoLogger {

  val restaurants = ArrayList<RestaurantModel>()

  override fun findAll(): List<RestaurantModel> {
    return restaurants
  }

  override fun create(restaurant: RestaurantModel) {
    restaurant.id = getId()
    restaurants.add(restaurant)
    logAll()
  }

  override fun update(restaurant: RestaurantModel) {
    var foundRestaurant: RestaurantModel? = restaurants.find { p -> p.id == restaurant.id }
    if (foundRestaurant != null) {
      foundRestaurant.title = restaurant.title
      foundRestaurant.description = restaurant.description
      foundRestaurant.image = restaurant.image
      foundRestaurant.ratingBar = restaurant.ratingBar      // RATING BAR
      foundRestaurant.lat = restaurant.lat
      foundRestaurant.lng = restaurant.lng
      foundRestaurant.zoom = restaurant.zoom
      logAll();
    }
  }

  override fun delete(restaurant: RestaurantModel) {
    restaurants.remove(restaurant)
  }
  
  fun logAll() {
    restaurants.forEach { info("${it}") }
  }
}