package org.wit.restaurant.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_restaurant.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.restaurant.R
import org.wit.restaurant.helpers.readImage
import org.wit.restaurant.helpers.readImageFromPath
import org.wit.restaurant.helpers.showImagePicker
import org.wit.restaurant.main.MainApp
import org.wit.restaurant.models.Location
import org.wit.restaurant.models.RestaurantModel

class RestaurantActivity : AppCompatActivity(), AnkoLogger {


  var restaurant = RestaurantModel()
  lateinit var app: MainApp
  val IMAGE_REQUEST = 1
  val LOCATION_REQUEST = 2
  var edit = false;

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_restaurant)
    toolbarAdd.title = title
    setSupportActionBar(toolbarAdd)
    info("Restaurant Activity started..")

    app = application as MainApp




    if (intent.hasExtra("restaurant_edit")) {
      edit = true
      restaurant = intent.extras.getParcelable<RestaurantModel>("restaurant_edit")
      restaurantTitle.setText(restaurant.title)
      description.setText(restaurant.description)
      restaurantImage.setImageBitmap(readImageFromPath(this, restaurant.image))
      if (restaurant.image != null) {
        chooseImage.setText(R.string.change_restaurant_image)
      }
      btnAdd.setText(R.string.save_restaurant)
    }

    btnAdd.setOnClickListener() {
      restaurant.title = restaurantTitle.text.toString()

      restaurant.description = description.text.toString()

        // restaurant.rating = ratingBar.text.toString()
        if (restaurant.title.isNullOrEmpty()) {
          toast("Please enter a Title")
          startActivity(intent)

        }
        else if (restaurant.description.isNullOrEmpty()) {
          toast("Please enter a Description")
          startActivity(intent)
        }

          else if (restaurant.image.isNullOrEmpty()){
                    toast("Please select an image")

        }
        else {
          if (edit) {
            app.restaurants.update(restaurant.copy())
          } else {

            app.restaurants.create(restaurant.copy())
          }
        }
        info("add Button Pressed: $restaurantTitle")
        setResult(AppCompatActivity.RESULT_OK)
        finish()
      }

      chooseImage.setOnClickListener {
        showImagePicker(this, IMAGE_REQUEST)
      }

      restaurantLocation.setOnClickListener {
        val location = Location(52.245696, -7.139102, 15f)
        if (restaurant.zoom != 0f) {
          location.lat = restaurant.lat
          location.lng = restaurant.lng
          location.zoom = restaurant.zoom
        }
        startActivityForResult(intentFor<MapsActivity>().putExtra("location", location), LOCATION_REQUEST)
      }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
      menuInflater.inflate(R.menu.menu_restaurant, menu)
/*
    val searchItem = menu.findItem(R.id.menu_search)

    if (searchItem != null) {
      val searchView = searchItem.actionView as SearchView
      searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{

        override fun onQueryTextChange(newText: String?): Boolean {

          if(newText!!.isNotEmpty()) {
        displayList.clear()
            val search = newText.toLowerCase()    //search is not case sensitive
            restaurant.forEach {
              if(it.toLowerCase().contains(search)) {
                displayList.add(it)

              }
            }
        restaurant list.adapter.notifyDataSetChanged()


          }




        }

        override fun onQueryTextSubmit(query: String?): Boolean {
          return true
        }

      })


    }
*/
      if (edit && menu != null) menu.getItem(0).setVisible(true)
      return super.onCreateOptionsMenu(menu)

    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
      when (item?.itemId) {
        R.id.item_delete -> {
          app.restaurants.delete(restaurant)
          finish()
        }
        R.id.item_cancel -> {
          finish()
        }
      }
      return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
      super.onActivityResult(requestCode, resultCode, data)
      when (requestCode) {
        IMAGE_REQUEST -> {
          if (data != null) {
            restaurant.image = data.getData().toString()
            restaurantImage.setImageBitmap(readImage(this, resultCode, data))
            chooseImage.setText(R.string.change_restaurant_image)
          }
        }
        LOCATION_REQUEST -> {
          if (data != null) {
            val location = data.extras.getParcelable<Location>("location")
            restaurant.lat = location.lat
            restaurant.lng = location.lng
            restaurant.zoom = location.zoom
          }
        }
      }
    }
  }


