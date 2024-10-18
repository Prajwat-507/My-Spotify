package com.example.myspotify.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.myspotify.R
import com.example.myspotify.databinding.MusicItemViewBinding
import com.example.myspotify.models.Data
import com.squareup.picasso.Picasso

class AdapterMusic(val musicList: List<Data>):
    RecyclerView.Adapter<AdapterMusic.MusicCategoryViewHolder>() {

    class MusicCategoryViewHolder(val binding: MusicItemViewBinding): ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicCategoryViewHolder {
        val view = MusicItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MusicCategoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return musicList.size
    }

    override fun onBindViewHolder(holder: MusicCategoryViewHolder, position: Int) {
        val currentItem = musicList[position]

        holder.binding.apply {
            Picasso.get().load(currentItem.album.cover_medium).into(IvMusicImage)
            tvMusicTitle.text = currentItem.album.title

            IvMusicImage.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("arg", currentItem.artist.name)

                holder.itemView.findNavController().navigate(R.id.action_homeFragment_to_playListFragment, bundle)

            }
        }
    }
}