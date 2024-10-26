package org.wit.hotels.views.map

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
// import com.google.android.gms.maps.UiSettings
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.wit.hotels.main.MainApp
import timber.log.Timber.i

class HotelsMapPresenter(val view: HotelsMapView) {
    var app: MainApp

    init {
        i("HotelsMapPresenter init started")
        app = view.application as MainApp
    }

    fun doPopulateMap(map: GoogleMap) {
        i("HotelsMapPresenter doPopulateMap started")
        map.uiSettings.isZoomControlsEnabled = true
        map.uiSettings.isCompassEnabled = true
        // UiSettings.setZoomControlsEnabled(true)
        map.setOnMarkerClickListener(view)
        app.hotels.findAll().forEach {
            val loc = LatLng(it.lat, it.lng)
            val options = MarkerOptions().title(it.name).position(loc)
            map.addMarker(options)?.tag = it.id
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
        }
    }

    fun doMarkerSelected(marker: Marker) {
        i("HotelsMapPresenter doMarkerSelected started")
        val tag = marker.tag as Long
        val hotels = app.hotels.findById(tag)
        if (hotels != null) view.showHotels(hotels)
    }
}