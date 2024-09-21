package com.example.myspotify

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
import androidx.media3.exoplayer.ExoPlayer
import com.example.myspotify.databinding.FragmentPlayerBinding
import com.squareup.picasso.Picasso


class PlayerFragment : Fragment() {
    private lateinit var binding: FragmentPlayerBinding
    private lateinit var exoPlayer: ExoPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPlayerBinding.inflate(layoutInflater)

        setStatusNavBar()
        handleSystemBar()

        val trackTitle = arguments?.getString("track_title")
        val artistName = arguments?.getString("at_name")
        val trackImage = arguments?.getString("track_Image")
        val preview = arguments?.getString("preview")

        playCurrentTrack(trackTitle, artistName, trackImage, preview)


        return binding.root
    }

    private fun playCurrentTrack(trackTitle: String?, artistName: String?, trackImage: String?, preview: String?) {

        Utils.startPlaying(requireContext(), preview!!)

        binding.apply {
            tvTrackArtist.text = artistName
            tvTrackTitle.text = trackTitle

            Picasso.get().load(trackImage).into(IvTrackImage)

            exoPlayer = Utils.ExoPlayerInstance(requireContext())!!
            binding.playerView.player = exoPlayer


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
                bottomMargin = insets.bottom
                rightMargin = insets.right
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