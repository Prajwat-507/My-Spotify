package com.example.myspotify.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.myspotify.Constants
import com.example.myspotify.R
import com.example.myspotify.databinding.ArtistImgViewBinding
import com.example.myspotify.models.ArtistCategory
import com.squareup.picasso.Picasso

class AdapterArtist(val artistCategory: ArrayList<ArtistCategory>):
    RecyclerView.Adapter<AdapterArtist.categoryViewHolder>(){

    class categoryViewHolder(val binding: ArtistImgViewBinding): ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): categoryViewHolder {
        val view = ArtistImgViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return categoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return artistCategory.size
    }

    override fun onBindViewHolder(holder: categoryViewHolder, position: Int) {
        val currentArtist = artistCategory[position]

        holder.binding.apply {
            Picasso.get().load(currentArtist.image).into(IvArtistImg)

            tvArtistTitle.text = currentArtist.title

            var cnt = 0

            IvArtistImg.setOnClickListener {
                Picasso.get().load(R.drawable.green_tick).into(IvArtistImg)
                Constants.artist.add(currentArtist.title.toString())
//                Utils.Log("ArrSIZE", Constants.artist.size.toString())
                cnt+=1
            }
        }

    }
}