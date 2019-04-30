package org.wit.restaurant.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.restaurant.models.RestaurantJSONStore
import org.wit.restaurant.models.RestaurantMemStore
import org.wit.restaurant.models.RestaurantStore

class MainApp : Application(), AnkoLogger {

  lateinit var restaurants: RestaurantStore

  override fun onCreate() {
    super.onCreate()
    restaurants = RestaurantJSONStore(applicationContext)
    info("Restaurant Rater started")
  }
}