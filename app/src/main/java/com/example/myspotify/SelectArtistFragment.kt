package com.example.myspotify

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myspotify.activity.MainActivity
import com.example.myspotify.adapter.AdapterArtist
import com.example.myspotify.databinding.ArtistImgViewBinding
import com.example.myspotify.databinding.FragmentSelectArtistBinding
import com.example.myspotify.models.ArtistCategory
import com.github.ybq.android.spinkit.SpinKitView
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.DoubleBounce
import com.github.ybq.android.spinkit.style.ThreeBounce


class SelectArtistFragment : Fragment() {
    private lateinit var binding: FragmentSelectArtistBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSelectArtistBinding.inflate(layoutInflater)



        setStatusNavBar()

        handleSystemBar()

        onNavigationBtnClicked()

        onNextBtnClicked()

        setArtistList()


        return binding.root

    }

    private fun setArtistList() {
//        Utils.showDialog(requireContext())

        val categoryList = ArrayList<ArtistCategory>()

        for(i in 0 until  Constants.MusicArtistName.size){
            categoryList.add(ArtistCategory(Constants.MusicArtistName[i], Constants.MusicArtistImage[i]))
        }


        binding.atRv.adapter = AdapterArtist(categoryList)
    }



    private fun onNextBtnClicked() {
        binding.btnCnfAt.setOnClickListener {
            findNavController().navigate(R.id.action_selectArtistFragment_to_selectPodcastFragment)
        }
    }


    private fun onNavigationBtnClicked(){
        binding.atToolbar.setNavigationOnClickListener {
            startActivity(Intent(requireActivity(), MainActivity::class.java))
            requireActivity().finish()
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