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
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.myspotify.adapter.PlayListAdapter
import com.example.myspotify.databinding.FragmentPlayListBinding
import com.example.myspotify.models.Data
import com.example.myspotify.viewModel.ApiDataViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch


class PlayListFragment : Fragment() {
    private lateinit var binding: FragmentPlayListBinding
    private var playListData = ArrayList<List<Data>>()

    val firebaseFirestore = Firebase.firestore

    private val viewModel: ApiDataViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPlayListBinding.inflate(layoutInflater)

        setStatusNavBar()
        handleSystemBar()

        // Access arguments (data passed from the adapter)
        val searchArgument = arguments?.getString("arg")


        getTrack(searchArgument!!)


        return binding.root
    }

    private fun getTrack(search_argument: String) {

        Utils.showDialog(requireContext())

        viewModel.apply {

            playListData = viewModel.getMusicApiData(search_argument)

            lifecycleScope.launch {
                successCallMusic.collect{
                    if(it) {
                        Utils.hideDialog()
                        binding.rvPl.adapter = PlayListAdapter(requireContext(), ArrayList(playListData[0]), firebaseFirestore)
                    }
                }
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