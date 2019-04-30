package org.wit.restaurant.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.wit.restaurant.helpers.*
import java.util.*

val JSON_FILE = "restaurants.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<java.util.ArrayList<RestaurantModel>>() {}.type

fun generateRandomId(): Long {
  return Random().nextLong()
}

class RestaurantJSONStore : RestaurantStore, AnkoLogger {

  val context: Context
  var restaurants = mutableListOf<RestaurantModel>()

  constructor (context: Context) {
    this.context = context
    if (exists(context, JSON_FILE)) {
      deserialize()
    }
  }

  override fun findAll(): MutableList<RestaurantModel> {
    return restaurants
  }

  override fun create(restaurant: RestaurantModel) {
    restaurant.id = generateRandomId()
    restaurants.add(restaurant)
    serialize()
  }

  override fun update(restaurant: RestaurantModel) {
    val restaurantsList = findAll() as ArrayList<RestaurantModel>
    var foundRestaurant: RestaurantModel? = restaurantsList.find { p -> p.id == restaurant.id }
    if (foundRestaurant != null) {
      foundRestaurant.title = restaurant.title
      foundRestaurant.description = restaurant.description
      foundRestaurant.image = restaurant.image
      foundRestaurant.lat = restaurant.lat
      foundRestaurant.lng = restaurant.lng
      foundRestaurant.zoom = restaurant.zoom
    }
    serialize()
  }

  override fun delete(restaurant: RestaurantModel) {
    restaurants.remove(restaurant)
    serialize()
  }

  private fun serialize() {
    val jsonString = gsonBuilder.toJson(restaurants, listType)
    write(context, JSON_FILE, jsonString)
  }

  private fun deserialize() {
    val jsonString = read(context, JSON_FILE)
    restaurants = Gson().fromJson(jsonString, listType)
  }
}
