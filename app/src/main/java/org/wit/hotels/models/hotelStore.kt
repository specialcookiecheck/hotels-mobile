package org.wit.hotels.models

interface HotelStore {

    fun findAll(): List<HotelModel>
    fun findById(id:Long) : HotelModel?
    fun create(hotel: HotelModel)
    fun update(hotel: HotelModel)
    fun delete(hotel: HotelModel)
}