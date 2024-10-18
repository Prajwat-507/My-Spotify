package com.example.myspotify

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.navigation.fragment.findNavController
import com.example.myspotify.activity.MainActivity
import com.example.myspotify.adapter.AdapterArtist
import com.example.myspotify.databinding.FragmentSelectPodcastBinding
import com.example.myspotify.models.ArtistCategory


class SelectPodcastFragment : Fragment() {
    private lateinit var binding : FragmentSelectPodcastBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSelectPodcastBinding.inflate(layoutInflater)

        setStatusNavBar()

        handleSystemBar()

        onNavigationBtnClicked()

        onNextBtnClicked()


        setPodcastList()

        return binding.root
    }


    private fun setPodcastList() {

        val podcastList = ArrayList<ArtistCategory>()

        for(i in 0 until  Constants.podcastArtistName.size){
            podcastList.add(ArtistCategory(Constants.podcastArtistName[i], Constants.podcastArtistImage[i]))
        }

        binding.pdRv.adapter = AdapterArtist(podcastList)
    }


    private fun onNextBtnClicked() {
        binding.btnCnfPod.setOnClickListener {
//            findNavController().navigate(R.id.action_selectPodcastFragment_to_homeFragment)
        }
    }


    private fun onNavigationBtnClicked(){
        binding.pdToolbar.setNavigationOnClickListener {
//            findNavController().navigate(R.id.action_selectPodcastFragment_to_selectArtistFragment)
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


    private fun handleSystemBar() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Apply the insets as a margin to the view. This solution sets
            // only the bottom, left, and right dimensions, but you can apply whichever
            // insets are appropriate to your layout. You can also update the view padding
            // if that's more appropriate.
            v.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                leftMargin = insets.left
                bottomMargin = insets.bottom
                rightMargin = insets.right
            }

            // Return CONSUMED if you don't want want the window insets to keep passing
            // down to descendant views.
            WindowInsetsCompat.CONSUMED
        }
    }
}