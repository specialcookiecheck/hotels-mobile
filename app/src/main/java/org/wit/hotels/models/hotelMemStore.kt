package org.wit.hotels.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class HotelMemStore : HotelStore {

    val hotels = ArrayList<HotelModel>()

    override fun findAll(): List<HotelModel> {
        return hotels
    }

    override fun findById(id:Long) : HotelModel? {
        val foundHotels: HotelModel? = hotels.find { it.hotelId == id }
        return foundHotels
    }

    override fun create(hotel: HotelModel) {
        hotel.hotelId = getId()
        hotels.add(hotel)
        logAll()
    }

    override fun update(hotel: HotelModel) {
        val foundHotels: HotelModel? = hotels.find { p -> p.hotelId == hotel.hotelId }
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