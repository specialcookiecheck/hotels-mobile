package org.wit.hotels.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import org.wit.hotels.helpers.*
import timber.log.Timber
import timber.log.Timber.i
import java.lang.reflect.Type
import java.util.*

const val HOTELS_JSON_FILE = "hotels.json"

class HotelJSONStore(private val context: Context) : HotelStore {

    private var hotels = mutableListOf<HotelModel>()
    private val listType: Type = object : TypeToken<ArrayList<HotelModel>>() {}.type

    init {
        i("HotelJSONStore init started")
        if (exists(context, HOTELS_JSON_FILE)) {
            deserialize()
        }
        i("printing hotels")
        i(hotels.toString())
    }

    override fun findAll(): MutableList<HotelModel> {
        i("HotelJSONStore findAll started")
        logAll()
        return hotels
    }

    override fun findById(id:Long) : HotelModel? {
        i("HotelJSONStore findById started")
        val foundHotels: HotelModel? = hotels.find { it.id == id }
        return foundHotels
    }

    override fun create(hotel: HotelModel) {
        i("HotelJSONStore create started")
        hotel.id = generateRandomId()
        hotels.add(hotel)
        serialize()
    }

    override fun update(hotel: HotelModel) {
        i("HotelJSONStore update started")
        val hotelsList = findAll() as ArrayList<HotelModel>
        var foundHotels: HotelModel? = hotelsList.find { p -> p.id == hotel.id }
        if (foundHotels != null) {
            foundHotels.name = hotel.name
            foundHotels.description = hotel.description
            foundHotels.street = hotel.street
            foundHotels.city = hotel.city
            foundHotels.state = hotel.state
            foundHotels.country = hotel.country
            foundHotels.email = hotel.email
            foundHotels.phone = hotel.phone
            foundHotels.image = hotel.image
            foundHotels.lat = hotel.lat
            foundHotels.lng = hotel.lng
            foundHotels.zoom = hotel.zoom
        }
        serialize()
    }

    private fun serialize() {
        i("HotelJSONStore serialize started")
        val jsonString = gsonBuilder.toJson(hotels, listType)
        write(context, HOTELS_JSON_FILE, jsonString)
    }

    private fun deserialize() {
        i("HotelJSONStore deserialize started")
        val jsonString = read(context, HOTELS_JSON_FILE)
        hotels = gsonBuilder.fromJson(jsonString, listType)
    }

    override fun delete(hotel: HotelModel) {
        i("HotelJSONStore delete started")
        hotels.remove(hotel)
        serialize()
    }

    private fun logAll() {
        i("HotelJSONStore logAll started")
        hotels.forEach { Timber.i("$it") }
    }
}

