package com.example.myspotify.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.myspotify.R
import com.example.myspotify.databinding.SearchItemViewBinding
import com.example.myspotify.models.SongCategory
import com.squareup.picasso.Picasso

class SearchAdapter(val songCategory: ArrayList<SongCategory>):
    RecyclerView.Adapter<SearchAdapter.categortViewHolder>(){

    class categortViewHolder(val binding: SearchItemViewBinding): ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): categortViewHolder {
        val view = SearchItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return categortViewHolder(view)
    }

    override fun getItemCount(): Int {
        return songCategory.size
    }

    override fun onBindViewHolder(holder: categortViewHolder, position: Int) {
        val currentCategory = songCategory[position]
        holder.binding.apply {
            Picasso.get().load(currentCategory.Image).into(songImage)
            cvSearch.setBackgroundResource(currentCategory.backColor)
            tvSongType.text = currentCategory.name

            cvSearch.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("arg", currentCategory.name)

                holder.itemView.findNavController().navigate(R.id.action_searchFragment_to_playListFragment, bundle)
            }
        }
    }

}