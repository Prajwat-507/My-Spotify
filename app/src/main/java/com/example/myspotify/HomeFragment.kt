package com.example.myspotify

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
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myspotify.adapter.AdapterMusic
import com.example.myspotify.databinding.FragmentHomeBinding
import com.example.myspotify.models.Data
import com.example.myspotify.models.MyData
import com.example.myspotify.viewModel.ApiDataViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.asTask
import kotlinx.coroutines.withContext
import nl.joery.animatedbottombar.AnimatedBottomBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var musicList : ArrayList<List<Data>>
    var podcastdataList = ArrayList<List<Data>>()
    var editorsPickdataList = ArrayList<List<Data>>()
    var episodesdataList = ArrayList<List<Data>>()

    private val viewModel: ApiDataViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)

//        setStatusNavBar()
//        handleSystemBar()

        handleBottomBar()

        getMusicData()

        getPodcastData()

        getEpisodes()




        return binding.root
    }



    private fun getMusicData() {
        Utils.showDialog(requireActivity())

        viewModel.apply {

            lifecycleScope.launch {
                successCallMusic.collect {
                    if(it){
//                        Utils.hideDialog()
                        val adapter = AdapterMusic(musicList)
                        binding.recyclerMusic.adapter = adapter
                        adapter.notifyDataSetChanged()
                    }
                    else{
                        for(i in 0 until Constants.MusicArtistName.size) {
                            musicList = viewModel.getMusicApiData(Constants.MusicArtistName[i])
                        }
                    }
                }
            }
        }
    }



    private fun getPodcastData() {
        viewModel.apply {

            lifecycleScope.launch {
                viewModel.successCallPodcast.collect {
                    if (it) {
                        val adapter = AdapterMusic(podcastdataList)
                        binding.recyclerPodcast.adapter = adapter
                        adapter.notifyDataSetChanged()
                    }
                    else{
                        for (i in 0 until Constants.MusicArtistName.size) {
                            podcastdataList = viewModel.getPodcastApiData(Constants.MusicArtistName[i])
                        }
                    }
                }
            }
        }
    }


    private fun getEpisodes() {
        viewModel.apply {

            lifecycleScope.launch {
                viewModel.successCallEpisode.collect {
                    if (it) {
                        Utils.hideDialog()
                        val adapter = AdapterMusic(episodesdataList)
                        binding.recyclerEpisodes.adapter = adapter
                        adapter.notifyDataSetChanged()
                    }
                    else{
                        for (i in 0 until Constants.MusicArtistName.size) {
                            episodesdataList = viewModel.getEpisodeApiData(Constants.MusicArtistName[i])
                        }
                    }
                }
            }
        }
    }


    private fun handleBottomBar() {
//        binding.bottomBar.onTabSelected = {
//            if(it.title.equals("search")) {
//                findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
//
//
//            }
//            if(it.title.equals("library")){
//                findNavController().navigate(R.id.action_homeFragment_to_libraryFragment)
//            }
//        }


        binding.bottomBar.setOnTabSelectListener(object : AnimatedBottomBar.OnTabSelectListener{

            override fun onTabSelected(lastIndex: Int, lastTab: AnimatedBottomBar.Tab?,
                                       newIndex: Int, newTab: AnimatedBottomBar.Tab
            ) {
                if(newTab.title.equals("search")) {

                    binding.bottomBar.selectTabAt(newIndex, animate = true)
                    findNavController().navigate(R.id.action_homeFragment_to_searchFragment)

                }
                if(newTab.title.equals("library")){


                    binding.bottomBar.selectTabAt(newIndex,   true)
                    findNavController().navigate(R.id.action_homeFragment_to_libraryFragment)

                }
            }

            override fun onTabReselected(index: Int, tab: AnimatedBottomBar.Tab) {
                Log.d("bottom_bar", "Reselected index: $index, title: ${tab.title}")
            }
        })
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
        window.navigationBarColor = ContextCompat.getColor(requireActivity(), R.color.yellow)
    }

}