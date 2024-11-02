package org.wit.hotels.main

import android.app.Application
import org.wit.hotels.models.HotelJSONStore
import org.wit.hotels.models.HotelMemStore
import org.wit.hotels.models.HotelStore
import org.wit.hotels.models.UserJSONStore
import org.wit.hotels.models.UserStore
import org.wit.users.models.UserMemStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    lateinit var hotels: HotelStore
    lateinit var users: UserStore

    override fun onCreate() {
        i("MainApp started")
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        hotels = HotelJSONStore(applicationContext)
        // hotels = HotelMemStore()
        // users = UserMemStore()
        users = UserJSONStore(applicationContext)
    }
}