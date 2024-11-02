package org.wit.hotels.views.hotels

import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import org.wit.hotels.main.MainApp
import org.wit.hotels.models.LocationModel
import org.wit.hotels.models.HotelModel
import org.wit.hotels.views.editLocation.EditLocationView
import org.wit.hotels.views.map.HotelsMapView
import timber.log.Timber
import timber.log.Timber.i

class HotelPresenter(private val view: HotelView) {

    var hotel = HotelModel()
    var app: MainApp = view.application as MainApp
    private lateinit var imageIntentLauncher : ActivityResultLauncher<PickVisualMediaRequest>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    var edit = false

    init {
        println("HotelPresenter init started")
        if (view.intent.hasExtra("hotel_edit")) {
            println("hotel_edit confirmed")
            edit = true
            //Requires API 33+
            //hotels = view.intent.getParcelableExtra("hotels_edit",HotelModel::class.java)!!
            hotel = view.intent.extras?.getParcelable("hotel_edit")!!
            view.showHotel(hotel)
        }
        registerImagePickerCallback()
        registerMapCallback()
    }

    fun doAddOrSave(name: String, description: String, street: String, city: String, state: String, country: String, email: String, phone: String) {
        println("HotelPresenter doAddOrSave started")
        hotel.hotelName = name
        hotel.hotelDescription = description
        hotel.hotelStreet = street
        hotel.hotelCity = city
        hotel.hotelState = state
        hotel.hotelCountry = country
        hotel.hotelEmail = email
        hotel.hotelPhone = phone

        if (edit) {
            app.hotels.update(hotel)
        } else {
            app.hotels.create(hotel)
        }
        view.setResult(RESULT_OK)
        view.finish()
    }

    fun doCancel() {
        println("HotelPresenter doCancel started")
        view.finish()
    }

    fun doDelete() {
        println("HotelPresenter doDelete started")
        view.setResult(99)
        app.hotels.delete(hotel)
        view.finish()
    }

    fun doSelectImage() {
        println("HotelPresenter doSelectImage started")
        val request = PickVisualMediaRequest.Builder()
            .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly)
            .build()
        imageIntentLauncher.launch(request)
    }

    fun doSetLocation() {
        println("HotelPresenter doSetLocation started")
        val location = LocationModel(54.0, -6.4, 6f)
        if (hotel.hotelZoomLevel != 0f) {
            location.lat =  hotel.hotelLatitude
            location.lng = hotel.hotelLongitude
            location.zoom = hotel.hotelZoomLevel
        }
        val launcherIntent = Intent(view, EditLocationView::class.java)
            .putExtra("hotel", hotel)
            // .putExtra("location", location)
        mapIntentLauncher.launch(launcherIntent)
    }

    fun cacheHotels (name: String, description: String, street: String, city: String, state: String, country: String, email: String, phone: String) {
        println("HotelPresenter cacheHotels started")
        hotel.hotelName = name
        hotel.hotelDescription = description
        hotel.hotelStreet = street
        hotel.hotelCity = city
        hotel.hotelState = state
        hotel.hotelCountry = country
        hotel.hotelEmail = email
        hotel.hotelPhone = phone
    }
    
    private fun registerImagePickerCallback() {
        println("HotelPresenter registerImagePickerCallback started")

        imageIntentLauncher = view.registerForActivityResult(
            ActivityResultContracts.PickVisualMedia()
        ) {
            try{
                view.contentResolver
                    .takePersistableUriPermission(it!!,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION )
                hotel.hotelImage = it // The returned Uri
                Timber.i("IMG :: ${hotel.hotelImage}")
                view.updateImage(hotel.hotelImage)
            }
            catch(e:Exception){
                e.printStackTrace()
            }
        }
    }

    private fun registerMapCallback() {
        println("HotelPresenter registerMapCallback started")

        mapIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Location ${result.data.toString()}")
                            //Requires API 33+
                            //val location = result.data!!.extras?.getParcelable("location",Location::class.java)!!
                            val location = result.data!!.extras?.getParcelable<LocationModel>("location")!!
                            Timber.i("Location == $location")
                            hotel.hotelLatitude = location.lat
                            hotel.hotelLongitude = location.lng
                            hotel.hotelZoomLevel = location.zoom
                        }
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
                }
            }
    }

    fun doShowHotelsMap() {
        i("HotelsListPresenter doShowHotelsMap started")
        val launcherIntent = Intent(view, HotelsMapView::class.java)
        mapIntentLauncher.launch(launcherIntent)
    }
}