package org.wit.hotels.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class HotelsMemStore : HotelsStore {

    val hotels = ArrayList<HotelModel>()

    override fun findAll(): List<HotelModel> {
        return hotels
    }

    override fun findById(id:Long) : HotelModel? {
        val foundHotels: HotelModel? = hotels.find { it.id == id }
        return foundHotels
    }

    override fun create(hotel: HotelModel) {
        hotel.id = getId()
        hotels.add(hotel)
        logAll()
    }

    override fun update(hotel: HotelModel) {
        val foundHotels: HotelModel? = hotels.find { p -> p.id == hotel.id }
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
            logAll()
        }
    }

    private fun logAll() {
        hotels.forEach { i("$it") }
    }

    override fun delete(hotel: HotelModel) {
        hotels.remove(hotel)
    }
}