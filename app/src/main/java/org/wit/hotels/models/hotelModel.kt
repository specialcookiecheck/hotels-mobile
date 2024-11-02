package org.wit.hotels.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HotelModel(var hotelId: Long = 0,
                      var hotelName: String = "",
                      var hotelDescription: String = "",
                      var hotelStreet: String = "",
                      var hotelCity: String = "",
                      var hotelState: String = "",
                      var hotelCountry: String = "",
                      var hotelEmail: String = "",
                      var hotelPhone: String = "",
                      var hotelImage: Uri = Uri.EMPTY,
                      var hotelLatitude : Double = 0.0,
                      var hotelLongitude: Double = 0.0,
                      var hotelZoomLevel: Float = 0f) : Parcelable

@Parcelize
data class LocationModel(var lat: Double = 0.0,
                         var lng: Double = 0.0,
                         var zoom: Float = 0f) : Parcelable

@Parcelize
data class UserModel(var userId: Long = 0,
                     var userFirstName: String = "",
                     var userLastName: String = "",
                     var userEmail: String = "",
                     var userPhone: String = "",
                     var userDateOfBirth: String = "") : Parcelable