package org.wit.hotels.views.hotelsList

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import org.wit.hotels.views.accounts.AccountView
import org.wit.hotels.views.hotels.HotelView
import org.wit.hotels.main.MainApp
import org.wit.hotels.models.HotelModel
import org.wit.hotels.models.UserModel
import org.wit.hotels.views.map.HotelsMapView
import timber.log.Timber.i

class HotelsListPresenter(val view: HotelsListView) {
    
    var app: MainApp
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    private var position: Int = 0
    
    init {
        i("HotelsListPresenter init started")
        app = view.application as MainApp
        registerMapCallback()
        registerRefreshCallback()
    }
    
    fun getHotels() = app.hotels.findAll()
    
    fun doAddHotel() {
        i("HotelsListPresenter doAddHotel started")
        val launcherIntent = Intent(view, HotelView::class.java)
        refreshIntentLauncher.launch(launcherIntent)
    }
    
    fun doEditHotel(hotel: HotelModel, pos: Int) {
        i("HotelsListPresenter doEditHotel started")
        val launcherIntent = Intent(view, HotelView::class.java)
        launcherIntent.putExtra("hotel_edit", hotel)
        position = pos
        refreshIntentLauncher.launch(launcherIntent)
    }

    fun doEditAccount() {
        i("HotelsListPresenter doEditAccount started")
        val launcherIntent = Intent(view, AccountView::class.java)
        val users = app.users.findAll()
        i(users.toString())
        if (users.size > 0) {
            i(users[0].toString())
            launcherIntent.putExtra("user_edit", users[0])
        } else {
            i("no existing users")
        }
        refreshIntentLauncher.launch(launcherIntent)
    }
    
    fun doShowHotelsMap() {
        i("HotelsListPresenter doShowHotelsMap started")
        val launcherIntent = Intent(view, HotelsMapView::class.java)
        mapIntentLauncher.launch(launcherIntent)
    }


    
    private fun registerRefreshCallback() {
        i("HotelsListPresenter registerRefreshCallback started")
        refreshIntentLauncher =
            view.registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) {
                if (it.resultCode == Activity.RESULT_OK) view.onRefresh()
                else // Deleting
                    if (it.resultCode == 99) view.onDelete(position)
            }
    }
    private fun registerMapCallback() {
        i("HotelsListPresenter registerMapCallback started")
        mapIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {  }
    }
}