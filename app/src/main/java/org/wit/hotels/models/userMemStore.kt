package org.wit.users.models

import org.wit.hotels.models.HotelModel
import org.wit.hotels.models.UserModel
import org.wit.hotels.models.UserStore
import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class UserMemStore : UserStore {

    val users = ArrayList<UserModel>()

    override fun findAll(): List<UserModel> {
        i("UserMemStore findAll started")
        return users
    }

    override fun findById(id:Long) : UserModel? {
        i("UserMemStore findById started")
        val foundUsers: UserModel? = users.find { it.userId == id }
        return foundUsers
    }

    override fun create(user: UserModel) {
        i("UserMemStore create started")
        user.userId = getId()
        users.add(user)
        logAll()
    }

    override fun update(user: UserModel) {
        i("UserMemStore update started")
        val foundUsers: UserModel? = users.find { p -> p.userId == user.userId }
        if (foundUsers != null) {
            foundUsers.userFirstName = user.userFirstName
            foundUsers.userLastName = user.userLastName
            foundUsers.userEmail = user.userEmail
            foundUsers.userPhone = user.userPhone
            foundUsers.userDateOfBirth = user.userDateOfBirth
            logAll()
        }
    }

    private fun logAll() {
        i("UserMemStore logAll started")
        users.forEach { i("$it") }
    }

    override fun delete(user: UserModel) {
        i("UserMemStore delete started")
        users.remove(user)
    }
}