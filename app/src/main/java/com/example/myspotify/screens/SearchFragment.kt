package com.example.myspotify.screens

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myspotify.Constants
import com.example.myspotify.R
import com.example.myspotify.adapter.SearchAdapter
import com.example.myspotify.databinding.FragmentSearchBinding
import com.example.myspotify.models.SongCategory
import nl.joery.animatedbottombar.AnimatedBottomBar


class SearchFragment : Fragment(){
    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(layoutInflater)

        handleBottomBar()

        populateData()

        return binding.root
    }

    private fun populateData(){
        val CategoryList = ArrayList<SongCategory>()


        for(i in 0 until  Constants.songType.size){
            CategoryList.add(SongCategory(Constants.songType[i], Constants.songTypeColor[i], Constants.songCategoryImage[i]))
        }

        binding.rvMusicCategory.adapter = SearchAdapter(CategoryList)
    }

    private fun handleBottomBar() {
        binding.bottomBar.setOnTabSelectListener(object : AnimatedBottomBar.OnTabSelectListener{

            override fun onTabSelected(lastIndex: Int, lastTab: AnimatedBottomBar.Tab?,
                                       newIndex: Int, newTab: AnimatedBottomBar.Tab
            ) {
                if(newTab.title.equals("home")){

                    findNavController().navigate(R.id.action_searchFragment_to_homeFragment)

                }
                if(newTab.title.equals("library")){

                    binding.bottomBar.selectTabAt(newIndex, animate = true)
                    findNavController().navigate(R.id.action_searchFragment_to_libraryFragment)
                }

            }

            override fun onTabReselected(index: Int, tab: AnimatedBottomBar.Tab) {
                Log.d("bottom_bar", "Reselected index: $index, title: ${tab.title}")
            }
        })
    }
}