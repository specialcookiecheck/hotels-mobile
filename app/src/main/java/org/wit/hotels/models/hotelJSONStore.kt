package org.wit.hotels.models

import android.content.Context
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import org.wit.hotels.helpers.*
import timber.log.Timber
import timber.log.Timber.i
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class HotelJSONStore(private val context: Context) : HotelStore {

    private var hotels = mutableListOf<HotelModel>()
    private var users = mutableListOf<UserModel>()
    var hotelHash : HashMap<String, List<HotelModel>> = HashMap()
    var userHash : HashMap<String, List<UserModel>> = HashMap()
    var dataList = mutableListOf(hotelHash, userHash)
    var dataHash : HashMap<String, List<Any>> = HashMap()
    var deserializedHash : HashMap<String, HashMap<String, List<Any>>> = HashMap()

    private val listType: Type = object : TypeToken<HashMap<String, List<Any>>>() {}.type


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
        i(hotels.toString())
        i(users.toString())
        hotelHash["hotels"] = hotels
        userHash["users"] = users
        dataHash["data"] = dataList
        val jsonString = gsonBuilder.toJson(dataHash)
        write(this.context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        i("HotelJSONStore deserialize started")
        i(hotels.toString())
        i(users.toString())
        val jsonString = read(context, JSON_FILE)
        i("JSONstring: " + jsonString)
        //hotels = gsonBuilder.fromJson(jsonString, listType)
        deserializedHash = gsonBuilder.fromJson(jsonString, listType)
        i(deserializedHash.toString())
        i(deserializedHash::class.simpleName)
        i(deserializedHash["data"].toString())
        i("deserialization print completed")
        //dataList = deserializedHash.
        //hotels = deserializedHash[0].
        //users = deserializedHash[data[1]].users
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

