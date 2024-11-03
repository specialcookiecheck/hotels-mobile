package org.wit.hotels.views.gallery

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.wit.hotels.main.MainApp
import org.wit.hotels.models.HotelModel
import org.wit.hotels.views.gallery.GalleryView
import timber.log.Timber.i

class GalleryPresenter(private val view: GalleryView) {

    var app: MainApp

    init {
        i("GalleryPresenter init started")
        app = view.application as MainApp
    }

    fun getHotels(): List<HotelModel> {
        i("GalleryPresenter getHotels started")
        val hotels = app.hotels.findAll()
        return hotels
    }
}