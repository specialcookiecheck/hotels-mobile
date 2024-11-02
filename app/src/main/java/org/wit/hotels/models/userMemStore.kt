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
        return users
    }

    override fun findById(id:Long) : UserModel? {
        val foundUsers: UserModel? = users.find { it.id == id }
        return foundUsers
    }

    override fun create(user: UserModel) {
        user.id = getId()
        users.add(user)
        logAll()
    }

    override fun update(user: UserModel) {
        val foundUsers: UserModel? = users.find { p -> p.id == user.id }
        if (foundUsers != null) {
            foundUsers.firstName = user.firstName
            foundUsers.lastName = user.lastName
            foundUsers.email = user.email
            foundUsers.phone = user.phone
            foundUsers.age = user.age
            logAll()
        }
    }

    private fun logAll() {
        users.forEach { i("$it") }
    }

    override fun delete(user: UserModel) {
        users.remove(user)
    }
}