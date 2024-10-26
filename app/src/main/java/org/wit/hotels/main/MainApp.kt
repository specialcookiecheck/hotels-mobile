package org.wit.hotels.main

import android.app.Application
import org.wit.hotels.models.HotelsJSONStore
import org.wit.hotels.models.HotelsMemStore
import org.wit.hotels.models.HotelsStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    lateinit var hotels: HotelsStore

    override fun onCreate() {
        i("MainApp started")
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        hotels = HotelsJSONStore(applicationContext)
        // hotels = HotelsMemStore()
    }
}