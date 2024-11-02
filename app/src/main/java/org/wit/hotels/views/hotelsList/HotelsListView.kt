package org.wit.hotels.views.hotelsList

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.hotels.R
import org.wit.hotels.adapters.HotelAdapter
import org.wit.hotels.adapters.HotelListener
import org.wit.hotels.databinding.ActivityHotelsListBinding
import org.wit.hotels.main.MainApp
import org.wit.hotels.models.HotelModel
import org.wit.hotels.models.UserModel
import timber.log.Timber.i

class HotelsListView : AppCompatActivity(), HotelListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityHotelsListBinding
    lateinit var presenter: HotelsListPresenter
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        i("HotelsListView onCreate started")
        super.onCreate(savedInstanceState)
        binding = ActivityHotelsListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        presenter = HotelsListPresenter(this)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        loadHotels()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        i("HotelsListView onCreateOptionsMenu started")
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        i("HotelsListView onOptionsItemSelected started")
        when (item.itemId) {
            R.id.hotel_add -> { presenter.doAddHotel() }
            R.id.hotel_map -> { presenter.doShowHotelsMap() }
            R.id.account -> { presenter.doEditAccount() }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onHotelClick(hotel: HotelModel, position: Int) {
        i("HotelsListView onHotelClick started")
        this.position = position
        presenter.doEditHotel(hotel, this.position)
    }

    private fun loadHotels() {
        i("HotelsListView loadHotels started")
        binding.recyclerView.adapter = HotelAdapter(presenter.getHotels(), this)
        onRefresh()
    }

    fun onRefresh() {
        i("HotelsListView onRefresh started")
        binding.recyclerView.adapter?.
        notifyItemRangeChanged(0,presenter.getHotels().size)
    }

    fun onDelete(position : Int) {
        i("HotelsListView onDelete started")
        binding.recyclerView.adapter?.notifyItemRemoved(position)
    }
}