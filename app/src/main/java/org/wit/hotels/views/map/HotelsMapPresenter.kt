package org.wit.hotels.views.map

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
// import com.google.android.gms.maps.UiSettings
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.wit.hotels.main.MainApp
import org.wit.hotels.models.HotelModel
import timber.log.Timber.i

class HotelsMapPresenter(val view: HotelsMapView) {
    var app: MainApp

    private lateinit var mapInit: GoogleMap

    init {
        i("HotelsMapPresenter init started")
        app = view.application as MainApp
    }

    fun doPopulateMap(map: GoogleMap) {
        i("HotelsMapPresenter doPopulateMap started")
        mapInit = map
        map.mapType = GoogleMap.MAP_TYPE_HYBRID
        map.uiSettings.isZoomControlsEnabled = true
        map.uiSettings.isCompassEnabled = true
        map.isTrafficEnabled = true
        // UiSettings.setZoomControlsEnabled(true)
        map.setOnMarkerClickListener(view)
        app.hotels.findAll().forEach {
            val loc = LatLng(it.hotelLatitude, it.hotelLongitude)
            val options = MarkerOptions().title(it.hotelName).position(loc)
            map.addMarker(options)?.tag = it.hotelId
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 6f))
        }
    }

    fun doMarkerSelected(marker: Marker) {
        i("HotelsMapPresenter doMarkerSelected started")
        val tag = marker.tag as Long
        val hotel = app.hotels.findById(tag)
        if (hotel != null) view.showHotel(hotel)
    }

    fun hotelSelectZoom(hotel: HotelModel) {
        i("HotelsMapPresenter hotelSelectZoom started")
        i("hotelZoom: " +hotel.hotelZoomLevel)
        val loc = LatLng(hotel.hotelLatitude, hotel.hotelLongitude)
        mapInit.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, hotel.hotelZoomLevel))
    }
}