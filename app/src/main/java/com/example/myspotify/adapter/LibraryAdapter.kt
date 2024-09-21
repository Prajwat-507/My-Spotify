package com.example.myspotify.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.myspotify.R
import com.example.myspotify.Utils
import com.example.myspotify.databinding.TrackItemViewBinding
import com.example.myspotify.models.UserData
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.squareup.picasso.Picasso

class LibraryAdapter(
    val context: Context,
    val firestoreOptions: FirestoreRecyclerOptions<UserData>
):
    FirestoreRecyclerAdapter<UserData, LibraryAdapter.libraryViewHolder>(firestoreOptions) {

    class libraryViewHolder (val binding: TrackItemViewBinding): ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): libraryViewHolder {
        val view = TrackItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return libraryViewHolder(view)
    }

    override fun onBindViewHolder(holder: libraryViewHolder, position: Int, model: UserData) {
        holder.binding.apply {
            Picasso.get().load(model.coverImg).into(trackImage)
            tvTrackTitle.text = model.trackTitle
            tvAtName.text = model.artistName
            trackDuration.text = model.duration

            favBtn.setImageResource(R.drawable.filled_favorite_24)

            llTrakView.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("track_title", model.trackTitle)
                bundle.putString("at_name", model.artistName)
                bundle.putString("track_Image", model.coverImg)
                bundle.putString("preview", model.preview)

                holder.itemView.findNavController().navigate(R.id.action_libraryFragment_to_playerFragment, bundle)
            }
        }

    }
}