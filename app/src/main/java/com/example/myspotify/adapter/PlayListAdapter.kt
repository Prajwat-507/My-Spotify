package com.example.myspotify.adapter

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.myspotify.Constants
import com.example.myspotify.R
import com.example.myspotify.Utils
import com.example.myspotify.databinding.TrackItemViewBinding
import com.example.myspotify.models.Data
import com.example.myspotify.models.UserData
import com.example.myspotify.screens.PlayListFragment
import com.example.myspotify.screens.PlayerFragment
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import java.util.Date

class PlayListAdapter(
    val fragmentManager: FragmentManager,
    val playList: List<Data>,
    val firebaseFirestore: FirebaseFirestore
):
    RecyclerView.Adapter<PlayListAdapter.PlayListViewholder>() {

    class PlayListViewholder(val binding: TrackItemViewBinding): ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayListViewholder {
        val view = TrackItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlayListViewholder(view)
    }

    override fun getItemCount(): Int {
        return  playList.size
    }

    override fun onBindViewHolder(holder: PlayListViewholder, position: Int) {
        val currentTrack = playList[position]

        holder.binding.apply {
            Picasso.get().load(currentTrack.album.cover_medium).into(trackImage)
            tvTrackTitle.text = currentTrack.title
            tvAtName.text = currentTrack.artist.name

            val trDuration = currentTrack.duration.toString()
            var format = ""
            if(trDuration.length>=3) {
                format = "${trDuration.get(0)}:${trDuration.get(1)}${trDuration.get(2)}"
            }else{
                format = "${trDuration.get(0)}:${trDuration.get(1)}0"
            }
            trackDuration.text = format

            Constants.artist.add(currentTrack.title)

            llTrakView.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("track_title", currentTrack.title)
                bundle.putString("at_name", currentTrack.artist.name)
                bundle.putString("track_Image", currentTrack.album.cover_medium)
                bundle.putString("preview", currentTrack.preview)

                Utils.replaceFragment(fragmentManager, PlayerFragment(), bundle)

//                holder.itemView.findNavController().navigate(R.id.action_playListFragment_to_playerFragment, bundle)
            }

            favBtn.setOnClickListener {
                favBtn.setImageResource(R.drawable.filled_favorite_24)

                val user = UserData(currentTrack.title, currentTrack.artist.name, currentTrack.album.cover_medium, currentTrack.duration.toString(),
                    Utils.getUserID(), Timestamp(Date()), currentTrack.preview)
                firebaseFirestore.collection("users").document().set(user)
                    .addOnSuccessListener {

                    }
                    .addOnFailureListener {
                        Log.w(TAG, "Error adding document", it)
                    }
            }
        }
    }


}