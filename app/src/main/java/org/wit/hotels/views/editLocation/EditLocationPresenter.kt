package org.wit.hotels.views.editLocation

import android.app.Activity
import android.content.Intent
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.wit.hotels.models.HotelModel
import org.wit.hotels.models.LocationModel
import timber.log.Timber.i
import kotlin.math.round

class EditLocationPresenter (val view: EditLocationView) {

    var location = LocationModel()
    //val location = LocationModel(54.0, -6.4, 6f)
    var hotel = HotelModel()

    init {
        i("EditLocationPresenter init started")
        //location = view.intent.extras?.getParcelable("location",Location::class.java)!!
        hotel = view.intent.extras?.getParcelable("hotel")!!
        // location = view.intent.extras?.getParcelable("location")!!

    }

    fun initMap(map: GoogleMap) {
        i("EditLocationPresenter initMap started")
        map.uiSettings.isZoomControlsEnabled = true
        map.uiSettings.isCompassEnabled = true
        location.lat = hotel.lat
        location.lng = hotel.lng
        val lat = round(hotel.lat *100) /100
        val lng = round(hotel.lng *100) /100
        val loc = LatLng(location.lat, location.lng)
        val options = MarkerOptions()
            .title(hotel.name + " - ($lat, $lng)")
            .snippet(hotel.description)
            .draggable(true)
            .position(loc)
        map.addMarker(options)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 6f))
        map.setOnMarkerDragListener(view)
        map.setOnMarkerClickListener(view)
    }

    fun doUpdateLocation(lat: Double, lng: Double, zoom: Float) {
        i("EditLocationPresenter doUpdateLocation started")
        location.lat = lat
        location.lng = lng
        location.zoom = zoom
    }

    fun doOnBackPressed() {
        i("EditLocationPresenter doOnBackPressed started")
        val resultIntent = Intent()
        resultIntent.putExtra("location", location)
        view.setResult(Activity.RESULT_OK, resultIntent)
        view.finish()
    }

    fun doUpdateMarker(marker: Marker) {
        i("EditLocationPresenter doUpdateMarker started")
        val description = hotel.description
        val loc = LatLng(location.lat, location.lng)
        val lat = round(location.lat *100) /100
        val lng = round(location.lng *100) /100
        marker.snippet = description
        marker.title = hotel.name + " - ($lat, $lng)"
    }
}