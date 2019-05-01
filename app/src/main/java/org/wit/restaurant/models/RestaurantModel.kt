package org.wit.restaurant.models

import android.os.Parcelable
import android.widget.RatingBar
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RestaurantModel(var id: Long = 0,
                          var title: String = "",
                          var description: String = "",
                          var image: String = "",
                           var ratingBar: String = "",     //RATING BAR
                          var lat : Double = 0.0,
                          var lng: Double = 0.0,
                          var zoom: Float = 0f) : Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable

