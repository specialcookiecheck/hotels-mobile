package org.wit.hotels.models

interface UserStore {
    fun findAll(): List<UserModel>
    fun findById(id:Long) : UserModel?
    fun create(hotel: UserModel)
    fun update(hotel: UserModel)
    fun delete(hotel: UserModel)
}