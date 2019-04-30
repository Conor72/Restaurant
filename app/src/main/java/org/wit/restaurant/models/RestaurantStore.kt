package org.wit.restaurant.models

interface RestaurantStore {
  fun findAll(): List<RestaurantModel>
  fun create(restaurant: RestaurantModel)
  fun update(restaurant: RestaurantModel)
  fun delete(restaurant: RestaurantModel)
}