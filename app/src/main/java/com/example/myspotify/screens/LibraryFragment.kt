package com.example.myspotify.screens

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myspotify.R
import com.example.myspotify.Utils
import com.example.myspotify.adapter.LibraryAdapter
import com.example.myspotify.databinding.FragmentLibraryBinding
import com.example.myspotify.models.UserData
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import nl.joery.animatedbottombar.AnimatedBottomBar
import okhttp3.internal.Util


class LibraryFragment : Fragment() {
    private lateinit var binding: FragmentLibraryBinding
    var firebaseFire: FirebaseFirestore? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLibraryBinding.inflate(layoutInflater)

        getUserLibrary()


        handleBottomBar()

        return binding.root
    }

    private fun getUserLibrary() {
        firebaseFire = FirebaseFirestore.getInstance()

        val query: Query = firebaseFire!!
            .collection("users")
            .whereEqualTo("userId", Utils.getUserID())
            .orderBy("created", Query.Direction.DESCENDING)
            .limit(10)



        val options = FirestoreRecyclerOptions.Builder<UserData>()
            .setQuery(query, UserData::class.java)
            .setLifecycleOwner(this)
            .build()

        val adapter = LibraryAdapter(requireContext(), options)
        binding.rvLibrary.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun handleBottomBar() {
        binding.bottomBar.setOnItemSelectedListener { item->
            when (item.itemId) {
                R.id.action_search -> {
                    Utils.replaceFragment(requireActivity().supportFragmentManager,SearchFragment())
                    true
                }
                R.id.action_home -> {
                    Utils.replaceFragment(requireActivity().supportFragmentManager,HomeFragment())
                    true
                }
                R.id.action_user -> {
                    Utils.replaceFragment(requireActivity().supportFragmentManager,UserFragment())
                    true
                }
                else -> false
            }
        }
    }
}