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

const val JSON_FILE = "hotels.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<HotelModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class HotelsJSONStore(private val context: Context) : HotelsStore {

    var hotels = mutableListOf<HotelModel>()

    init {
        i("HotelsJSONStore init started")
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<HotelModel> {
        i("HotelsJSONStore findAll started")
        logAll()
        return hotels
    }

    override fun findById(id:Long) : HotelModel? {
        i("HotelsJSONStore findById started")
        val foundHotels: HotelModel? = hotels.find { it.id == id }
        return foundHotels
    }

    override fun create(hotel: HotelModel) {
        i("HotelsJSONStore create started")
        hotel.id = generateRandomId()
        hotels.add(hotel)
        serialize()
    }

    override fun update(hotel: HotelModel) {
        i("HotelsJSONStore update started")
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
        i("HotelsJSONStore serialize started")
        val jsonString = gsonBuilder.toJson(hotels, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        i("HotelsJSONStore deserialize started")
        val jsonString = read(context, JSON_FILE)
        hotels = gsonBuilder.fromJson(jsonString, listType)
    }

    override fun delete(hotel: HotelModel) {
        i("HotelsJSONStore delete started")
        hotels.remove(hotel)
        serialize()
    }

    private fun logAll() {
        i("HotelsJSONStore logAll started")
        hotels.forEach { Timber.i("$it") }
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}