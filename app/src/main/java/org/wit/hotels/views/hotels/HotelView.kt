package org.wit.hotels.views.hotels

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.hotels.R
import org.wit.hotels.databinding.ActivityHotelBinding
import org.wit.hotels.models.HotelModel
import timber.log.Timber.i

class HotelView : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityHotelBinding
    private lateinit var presenter: HotelPresenter
    var hotelInit = HotelModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        println("HotelView onCreate started")
        super.onCreate(savedInstanceState)


        binding = ActivityHotelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        presenter = HotelPresenter(this)

        binding.addHotelImage.setOnClickListener {
            presenter.cacheHotels(binding.hotelName.text.toString(),
                binding.hotelDescription.text.toString(),
                binding.hotelStreet.text.toString(),
                binding.hotelCity.text.toString(),
                binding.hotelState.text.toString(),
                binding.hotelCountry.text.toString(),
                binding.hotelPhone.text.toString(),
                binding.hotelEmail.text.toString(),
                )
            presenter.doSelectImage()
        }

        binding.hotelLocation.setOnClickListener {
            presenter.cacheHotels(binding.hotelName.text.toString(),
                binding.hotelDescription.text.toString(),
                binding.hotelStreet.text.toString(),
                binding.hotelCity.text.toString(),
                binding.hotelState.text.toString(),
                binding.hotelCountry.text.toString(),
                binding.hotelPhone.text.toString(),
                binding.hotelEmail.text.toString(),
                )
            presenter.doSetLocation()
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.hotelViewMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        println("HotelView onCreateOptionsMenu started")
        menuInflater.inflate(R.menu.menu_hotel, menu)
        val deleteMenu: MenuItem = menu.findItem(R.id.hotel_delete)
        deleteMenu.isVisible = presenter.edit
        return super.onCreateOptionsMenu(menu)
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        i("HotelView onOptionsItemSelected started")
        when (item.itemId) {
            R.id.hotel_save -> {
                if (binding.hotelName.text.toString().isEmpty()) {
                    Snackbar.make(binding.root, R.string.enter_hotel_name, Snackbar.LENGTH_LONG)
                        .show()
                } else if (binding.hotelCoordinates.text.toString().isEmpty()) {
                    Snackbar.make(binding.root, R.string.enter_hotel_coordinates, Snackbar.LENGTH_LONG)
                        .show()
                } else {
                    presenter.doAddOrSave(
                        binding.hotelName.text.toString(),
                        binding.hotelDescription.text.toString(),
                        binding.hotelStreet.text.toString(),
                        binding.hotelCity.text.toString(),
                        binding.hotelState.text.toString(),
                        binding.hotelCountry.text.toString(),
                        binding.hotelEmail.text.toString(),
                        binding.hotelPhone.text.toString(),
                        )
                }
            }
            R.id.hotel_delete -> {
                presenter.doDelete()
            }
            R.id.hotel_cancel -> {
                presenter.doCancel()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun showHotel(hotel: HotelModel) {
        i("HotelView showHotel started")
        hotelInit = hotel

        binding.hotelName.setText(hotel.hotelName)
        binding.hotelDescription.setText(hotel.hotelDescription)
        binding.hotelStreet.setText(hotel.hotelStreet)
        binding.hotelCity.setText(hotel.hotelCity)
        binding.hotelState.setText(hotel.hotelState)
        binding.hotelCountry.setText(hotel.hotelCountry)
        binding.hotelEmail.setText(hotel.hotelEmail)
        binding.hotelPhone.setText(hotel.hotelPhone)
        val hotelCoordinatesText = "${hotel.hotelLatitude}, ${hotel.hotelLongitude}"
        binding.hotelCoordinates.text = hotelCoordinatesText
        Picasso.get()
            .load(hotel.hotelImage)
            .into(binding.hotelImage)
        if (hotel.hotelImage != Uri.EMPTY) {
            binding.addHotelImage.setText(R.string.change_hotel_image)
        }

    }

    fun updateImage(image: Uri){
        i("HotelView updateImage started")
        i("Image updated")
        Picasso.get()
            .load(image)
            .into(binding.hotelImage)
        binding.addHotelImage.setText(R.string.change_hotel_image)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        i("HotelView onMapReady started")
        i(hotelInit.toString())
        i(hotelInit.hotelName)
        val loc = LatLng(hotelInit.hotelLatitude, hotelInit.hotelLongitude)
        googleMap.addMarker(
            MarkerOptions()
                .position(loc)
                .title(hotelInit.hotelName)
        )
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, hotelInit.hotelZoomLevel))
    }
}