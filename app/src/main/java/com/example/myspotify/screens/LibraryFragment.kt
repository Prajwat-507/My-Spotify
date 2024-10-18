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
        binding.bottomBar.setOnTabSelectListener(object : AnimatedBottomBar.OnTabSelectListener{

            override fun onTabSelected(lastIndex: Int, lastTab: AnimatedBottomBar.Tab?,
                                       newIndex: Int, newTab: AnimatedBottomBar.Tab
            ) {
                if(newTab.title.equals("home")){
                    binding.bottomBar.selectTabAt(newIndex, animate = true)
                    findNavController().navigate(R.id.action_libraryFragment_to_homeFragment)
                }
                if(newTab.title.equals("search")){
                    binding.bottomBar.selectTabAt(newIndex, animate = true)
                    findNavController().navigate(R.id.action_libraryFragment_to_searchFragment)

                }

            }

            override fun onTabReselected(index: Int, tab: AnimatedBottomBar.Tab) {
                Log.d("bottom_bar", "Reselected index: $index, title: ${tab.title}")
            }
        })
    }
}