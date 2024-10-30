package org.wit.hotels.views.editLocation

import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import org.wit.hotels.R
import org.wit.hotels.models.HotelModel
import org.wit.hotels.models.LocationModel
import timber.log.Timber.i
import kotlin.math.round

class EditLocationView : AppCompatActivity(), OnMapReadyCallback,
    GoogleMap.OnMarkerDragListener,
    GoogleMap.OnMarkerClickListener {

    private lateinit var map: GoogleMap
    lateinit var presenter: EditLocationPresenter
    var location = LocationModel()
    var hotel = HotelModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        i("EditLocationView onCreate started")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        presenter = EditLocationPresenter(this)
        //Requires API 33+
        //location = intent.extras?.getParcelable("location",Location::class.java)!!
        //location = intent.extras?.getParcelable<LocationModel>("location")!!
        // val location = LocationModel(54.0, -6.4, 6f)
        // hotel = intent.extras?.getParcelable<HotelModel>("hotel")!!
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        onBackPressedDispatcher.addCallback(this ) {
            presenter.doOnBackPressed()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        i("EditLocationView onMapReady started")
        map = googleMap
        presenter.initMap(map)
    }

    override fun onMarkerDragStart(marker: Marker) {
        i("EditLocationView onMarkerDragStart started")
        location = LocationModel(marker.position.latitude, marker.position.longitude, 8f)
        val loc = LatLng(marker.position.latitude, marker.position.longitude)
        val name = marker.title
        marker.snippet = "Drag to update location for $name"
        map.moveCamera(CameraUpdateFactory.newLatLng(loc))
    }

    override fun onMarkerDrag(marker: Marker) {
        i("EditLocationView onMarkerDrag started")

    }

    override fun onMarkerDragEnd(marker: Marker) {
        i("EditLocationView onMarkerDragEnd started")
        val lat = round(marker.position.latitude)
        val lng = round(marker.position.longitude)
        marker.snippet = "New location: $lat, $lng (click to confirm)"
        presenter.doUpdateLocation(marker.position.latitude,
            marker.position.longitude,
            map.cameraPosition.zoom)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        i("EditLocationView onMarkerClick started")
        location.zoom = 12f
        val loc = LatLng(marker.position.latitude, marker.position.longitude)
        presenter.doUpdateMarker(marker)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, location.zoom))
        return false
    }
}