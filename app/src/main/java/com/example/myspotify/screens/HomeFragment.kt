package com.example.myspotify.screens

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.myspotify.Constants
import com.example.myspotify.R
import com.example.myspotify.Utils
import com.example.myspotify.adapter.AdapterMusic
import com.example.myspotify.databinding.FragmentHomeBinding
import com.example.myspotify.models.Data
import com.example.myspotify.viewModel.CategoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.launch
import nl.joery.animatedbottombar.AnimatedBottomBar

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    val categoryViewModel: CategoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)

        setStatusNavBar()
        handleSystemBar()
        handleBottomBar()


        getMusicData()

        return binding.root
    }


    private fun getMusicData() {
        viewLifecycleOwner.lifecycleScope.launch {
                categoryViewModel.musicCategory.collect { categories ->
                    // Update your UI with the categories list
                    if (categories.size>5) {
                        Utils.hideDialog()
                        val adapter = AdapterMusic(categories)
                        binding.recyclerMusic.adapter = adapter
                    }else{
                        Utils.showDialog(requireActivity())
                    }
                }
        }
    }



    private fun handleBottomBar() {
        binding.bottomBar.setOnItemSelectedListener { item->
            when (item.itemId) {
                R.id.action_search -> {
//                    findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
                    Utils.replaceFragment(requireActivity().supportFragmentManager, SearchFragment())
                    true
                }
                R.id.action_playlist -> {
//                    findNavController().navigate(R.id.action_homeFragment_to_playListFragment)
                    Utils.replaceFragment(requireActivity().supportFragmentManager, LibraryFragment())
                    true
                }
                R.id.action_user -> {
                    Utils.replaceFragment(requireActivity().supportFragmentManager, UserFragment())
                    true

                }
                else -> false
            }
        }
    }


    private fun handleSystemBar() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Apply the insets as a margin to the view. This solution sets
            // only the bottom, left, and right dimensions, but you can apply whichever
            // insets are appropriate to your layout. You can also update the view padding
            // if that's more appropriate.
            v.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                leftMargin = insets.left
                rightMargin = insets.right
                topMargin = insets.top
                bottomMargin = insets.bottom
            }
            // Return CONSUMED if you don't want want the window insets to keep passing
            // down to descendant views.
            WindowInsetsCompat.CONSUMED
        }
    }

    private fun setStatusNavBar(){
        // Change the status bar color
        val window = requireActivity().window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(requireActivity(), R.color.bg_color)
        window.navigationBarColor = ContextCompat.getColor(requireActivity(), R.color.black)
    }


}