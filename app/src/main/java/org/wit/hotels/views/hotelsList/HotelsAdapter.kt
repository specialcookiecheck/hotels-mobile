package org.wit.hotels.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.wit.hotels.databinding.CardHotelsBinding
import org.wit.hotels.models.HotelModel

interface HotelListener {
    fun onHotelClick(hotel: HotelModel, position : Int)
}

class HotelAdapter constructor(private var hotels: List<HotelModel>,
                                   private val listener: HotelListener) :
        RecyclerView.Adapter<HotelAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardHotelsBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val hotels = hotels[holder.adapterPosition]
        holder.bind(hotels, listener)
    }

    override fun getItemCount(): Int = hotels.size

    class MainHolder(private val binding : CardHotelsBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(hotel: HotelModel, listener: HotelListener) {
            binding.hotelName.text = hotel.name
            binding.hotelDescription.text = hotel.description
            Picasso.get().load(hotel.image).resize(200,200).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onHotelClick(hotel,adapterPosition) }
        }
    }
}
