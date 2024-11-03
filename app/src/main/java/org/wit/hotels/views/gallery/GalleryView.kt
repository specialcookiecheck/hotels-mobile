package org.wit.hotels.views.gallery

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import org.wit.hotels.databinding.ActivityViewGalleryBinding
import org.wit.hotels.main.MainApp
import timber.log.Timber.i


class GalleryView : AppCompatActivity() {
    lateinit var app: MainApp

    private lateinit var binding: ActivityViewGalleryBinding
    private lateinit var presenter: GalleryPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        i("GalleryView onCreate started")
        super.onCreate(savedInstanceState)

        binding = ActivityViewGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        presenter = GalleryPresenter(this)
        showGallery()
    }

    fun showGallery() {
        i("GalleryView showGallery started")
        presenter.getHotels().forEachIndexed() { index, hotel ->
            i(hotel.image.toString())
            var newImageView: ImageView = ImageView(this)
            newImageView.setImageURI(hotel.image)

            val layoutParams = LinearLayout.LayoutParams(100, 100)
            newImageView.layoutParams = layoutParams
            newImageView.layoutParams.height = 500;
            newImageView.layoutParams.width = 500;

            binding.galleryLayout.addView(newImageView)
        }
    }
}