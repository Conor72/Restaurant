package org.wit.restaurant.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import kotlinx.android.synthetic.main.activity_restaurant_list.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.wit.restaurant.R
import org.wit.restaurant.main.MainApp
import org.wit.restaurant.models.RestaurantModel

class RestaurantListActivity : AppCompatActivity(), RestaurantListener {

  lateinit var app: MainApp

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_restaurant_list)
    app = application as MainApp
    toolbarMain.title = title
    setSupportActionBar(toolbarMain)

    val layoutManager = LinearLayoutManager(this)
    recyclerView.layoutManager = layoutManager
    recyclerView.adapter = RestaurantAdapter(app.restaurants.findAll(), this)
    loadRestaurants()
  }

  private fun loadRestaurants() {
    showRestaurants( app.restaurants.findAll())
  }

  fun showRestaurants (restaurants: List<RestaurantModel>) {
    recyclerView.adapter = RestaurantAdapter(restaurants, this)
    recyclerView.adapter?.notifyDataSetChanged()
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.item_add -> startActivityForResult<RestaurantActivity>(0)
      R.id.item_map -> startActivity<RestaurantMapsActivity>()
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onRestaurantClick(restaurant: RestaurantModel) {
    startActivityForResult(intentFor<RestaurantActivity>().putExtra("restaurant_edit", restaurant), 0)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    loadRestaurants()
    super.onActivityResult(requestCode, resultCode, data)
  }
}