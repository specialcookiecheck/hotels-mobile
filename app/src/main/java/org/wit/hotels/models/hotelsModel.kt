package org.wit.hotels.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HotelModel(var id: Long = 0,
                      var name: String = "",
                      var description: String = "",
                      var street: String = "",
                      var city: String = "",
                      var state: String = "",
                      var country: String = "",
                      var email: String = "",
                      var phone: String = "",
                      var image: Uri = Uri.EMPTY,
                      var lat : Double = 0.0,
                      var lng: Double = 0.0,
                      var zoom: Float = 0f) : Parcelable

@Parcelize
data class LocationModel(var lat: Double = 0.0,
                         var lng: Double = 0.0,
                         var zoom: Float = 0f) : Parcelable