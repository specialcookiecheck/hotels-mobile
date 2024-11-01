package org.wit.hotels.views.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback
import com.google.android.gms.maps.StreetViewPanorama
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.squareup.picasso.Picasso
import org.wit.hotels.R
import org.wit.hotels.databinding.ActivityHotelsOverviewMapBinding
import org.wit.hotels.databinding.ContentHotelsOverviewMapBinding
import org.wit.hotels.main.MainApp
import org.wit.hotels.models.HotelModel
import timber.log.Timber.i

class HotelsMapView : AppCompatActivity() , GoogleMap.OnMarkerClickListener,
     OnStreetViewPanoramaReadyCallback {

    private lateinit var binding: ActivityHotelsOverviewMapBinding
    private lateinit var contentBinding: ContentHotelsOverviewMapBinding
    lateinit var app: MainApp
    lateinit var presenter: HotelsMapPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        i("HotelsMapView onCreate started")
        super.onCreate(savedInstanceState)
        app = application as MainApp
        binding = ActivityHotelsOverviewMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        presenter = HotelsMapPresenter(this)

        contentBinding = ContentHotelsOverviewMapBinding.bind(binding.root)

        contentBinding.mapView.onCreate(savedInstanceState)
        contentBinding.mapView.getMapAsync{
            presenter.doPopulateMap(it)
        }
/** when the code below is uncommented the app errors out when trying to connect...
        val streetViewPanoramaFragment =
            supportFragmentManager
                .findFragmentById(R.id.streetviewpanorama) as SupportStreetViewPanoramaFragment
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(this)
        */
    }


    fun showHotel(hotel: HotelModel) {
        i("HotelsMapView showHotel started")
        contentBinding.currentName.text = hotel.name
        contentBinding.currentDescription.text = hotel.description
        Picasso.get()
            .load(hotel.image)
            .into(contentBinding.currentImage)
        presenter.hotelSelectZoom(hotel)
    }

    override fun onStreetViewPanoramaReady(streetViewPanorama: StreetViewPanorama) {
        val sanFrancisco = LatLng(37.754130, -122.447129)
        streetViewPanorama.setPosition(sanFrancisco)
        }

    override fun onMarkerClick(marker: Marker): Boolean {
        i("HotelsMapView onMarkerClick started")
        presenter.doMarkerSelected(marker)
        return true
    }

    override fun onDestroy() {
        i("HotelsMapView onDestroy started")
        super.onDestroy()
        contentBinding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        i("HotelsMapView onLowMemory started")
        super.onLowMemory()
        contentBinding.mapView.onLowMemory()
    }

    override fun onPause() {
        i("HotelsMapView onPause started")
        super.onPause()
        contentBinding.mapView.onPause()
    }

    override fun onResume() {
        i("HotelsMapView onResume started")
        super.onResume()
        contentBinding.mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        i("HotelsMapView onSaveInstanceState started")
        super.onSaveInstanceState(outState)
        contentBinding.mapView.onSaveInstanceState(outState)
    }
}