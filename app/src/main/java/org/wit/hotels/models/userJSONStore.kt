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

const val USERS_JSON_FILE = "users.json"

class UserJSONStore(private val context: Context) : UserStore {

    private var users = mutableListOf<UserModel>()
    private val listType: Type = object : TypeToken<ArrayList<UserModel>>() {}.type

    init {
        i("UserJSONStore init started")
        if (exists(context, USERS_JSON_FILE)) {
            deserialize()
        }
        i("printing users")
        i(users.toString())
    }

    override fun findAll(): MutableList<UserModel> {
        i("UserJSONStore findAll started")
        logAll()
        i("UserJSONStore findAll completed")
        return users
    }

    override fun findById(id:Long) : UserModel? {
        i("UserJSONStore findById started")
        val foundUsers: UserModel? = users.find { it.id == id }
        return foundUsers
    }

    override fun create(user: UserModel) {
        i("UserJSONStore create started")
        user.id = generateRandomId()
        users.add(user)
        serialize()
    }

    override fun update(user: UserModel) {
        i("UserJSONStore update started")
        val usersList = findAll() as ArrayList<UserModel>
        var foundUsers: UserModel? = usersList.find { p -> p.id == user.id }
        if (foundUsers != null) {
            foundUsers.firstName = user.firstName
            foundUsers.lastName = user.lastName
            foundUsers.email = user.email
            foundUsers.phone = user.phone
            foundUsers.dateOfBirth = user.dateOfBirth
        }
        serialize()
    }

    private fun serialize() {
        i("UserJSONStore serialize started")
        val jsonString = gsonBuilder.toJson(users, listType)
        write(context, USERS_JSON_FILE, jsonString)
    }

    private fun deserialize() {
        i("UserJSONStore deserialize started")
        val jsonString = read(context, USERS_JSON_FILE)
        users = gsonBuilder.fromJson(jsonString, listType)
    }

    override fun delete(user: UserModel) {
        i("UserJSONStore delete started")
        users.remove(user)
        serialize()
    }

    private fun logAll() {
        i("UserJSONStore logAll started")
        users.forEach { i("$it") }
        i("UserJSONStore logAll completed")
    }
}
