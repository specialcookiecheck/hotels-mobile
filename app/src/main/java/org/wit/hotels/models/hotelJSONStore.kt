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


class HotelJSONStore(private val context: Context) : HotelStore {

    private var hotels = mutableListOf<HotelModel>()
    private val listType: Type = object : TypeToken<ArrayList<HotelModel>>() {}.type

    init {
        i("HotelJSONStore init started")
        if (exists(context, JSON_FILE)) {
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
        val foundHotels: HotelModel? = hotels.find { it.hotelId == id }
        return foundHotels
    }

    override fun create(hotel: HotelModel) {
        i("HotelJSONStore create started")
        hotel.hotelId = generateRandomId()
        hotels.add(hotel)
        serialize()
    }

    override fun update(hotel: HotelModel) {
        i("HotelJSONStore update started")
        val hotelsList = findAll() as ArrayList<HotelModel>
        var foundHotels: HotelModel? = hotelsList.find { p -> p.hotelId == hotel.hotelId }
        if (foundHotels != null) {
            foundHotels.hotelName = hotel.hotelName
            foundHotels.hotelDescription = hotel.hotelDescription
            foundHotels.hotelStreet = hotel.hotelStreet
            foundHotels.hotelCity = hotel.hotelCity
            foundHotels.hotelState = hotel.hotelState
            foundHotels.hotelCountry = hotel.hotelCountry
            foundHotels.hotelEmail = hotel.hotelEmail
            foundHotels.hotelPhone = hotel.hotelPhone
            foundHotels.hotelImage = hotel.hotelImage
            foundHotels.hotelLatitude = hotel.hotelLatitude
            foundHotels.hotelLongitude = hotel.hotelLongitude
            foundHotels.hotelZoomLevel = hotel.hotelZoomLevel
        }
        serialize()
    }

    private fun serialize() {
        i("HotelJSONStore serialize started")
        val jsonString = gsonBuilder.toJson(hotels, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        i("HotelJSONStore deserialize started")
        val jsonString = read(context, JSON_FILE)
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

