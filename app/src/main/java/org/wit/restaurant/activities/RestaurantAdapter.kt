package org.wit.restaurant.activities

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import kotlinx.android.synthetic.main.activity_restaurant.view.*
import kotlinx.android.synthetic.main.card_restaurant.view.*
import kotlinx.android.synthetic.main.card_restaurant.view.description

import org.wit.restaurant.R
import org.wit.restaurant.helpers.readImageFromPath
import org.wit.restaurant.models.RestaurantModel

interface RestaurantListener {
  fun onRestaurantClick(restaurant: RestaurantModel)
}

class RestaurantAdapter constructor(private var restaurants: List<RestaurantModel>,
                                   private val listener: RestaurantListener) : RecyclerView.Adapter<RestaurantAdapter.MainHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
    return MainHolder(LayoutInflater.from(parent?.context).inflate(R.layout.card_restaurant, parent, false))
  }

  override fun onBindViewHolder(holder: MainHolder, position: Int) {
    val restaurant = restaurants[holder.adapterPosition]
    holder.bind(restaurant, listener)
  }

  override fun getItemCount(): Int = restaurants.size

  class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(restaurant: RestaurantModel, listener: RestaurantListener) {
     // itemView.restaurantTitle.text = restaurant.title
      itemView.description.text = restaurant.description
     // itemView.ratingBar.text = restaurant.rating
      itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, restaurant.image))
      itemView.setOnClickListener { listener.onRestaurantClick(restaurant) }
    }
  }
}