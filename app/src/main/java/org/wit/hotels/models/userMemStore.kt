package org.wit.users.models

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
        val foundUsers: UserModel? = users.find { it.id == id }
        return foundUsers
    }

    override fun create(user: UserModel) {
        i("UserMemStore create started")
        user.id = getId()
        users.add(user)
        logAll()
    }

    override fun update(user: UserModel) {
        i("UserMemStore update started")
        val foundUsers: UserModel? = users.find { p -> p.id == user.id }
        if (foundUsers != null) {
            foundUsers.firstName = user.firstName
            foundUsers.lastName = user.lastName
            foundUsers.email = user.email
            foundUsers.phone = user.phone
            foundUsers.dateOfBirth = user.dateOfBirth
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