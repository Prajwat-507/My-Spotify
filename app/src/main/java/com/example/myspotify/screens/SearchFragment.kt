package com.example.myspotify.screens

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import com.example.myspotify.Constants
import com.example.myspotify.R
import com.example.myspotify.Utils
import com.example.myspotify.adapter.SearchAdapter
import com.example.myspotify.databinding.FragmentSearchBinding
import com.example.myspotify.models.SongCategory
import com.example.myspotify.viewModel.ListViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import okhttp3.internal.Util


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(layoutInflater)

        handleBottomBar()

        populateData()

        searchMusic()

        return binding.root
    }

    private fun searchMusic() {
        binding.tvSearch.setOnEditorActionListener { editText, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || event?.keyCode == KeyEvent.KEYCODE_ENTER) {

                // Get the entered text
                val query = editText.text.toString()

                // Perform the search action
                val bundle = Bundle()
                bundle.putString("arg", query)

                Utils.replaceFragment(parentFragmentManager,PlayListFragment(), bundle)

                // Hide the keyboard after search
                val imm =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view?.windowToken, 0)

                true // Return true to indicate the action was handled
            } else {
                false // Pass through other actions
            }
        }
    }

    private fun populateData() {
        val CategoryList = ArrayList<SongCategory>()


        for (i in 0 until Constants.songType.size) {
            CategoryList.add(
                SongCategory(
                    Constants.songType[i],
                    Constants.songTypeColor[i],
                    Constants.songCategoryImage[i]
                )
            )
        }

        binding.rvMusicCategory.adapter = SearchAdapter(requireActivity().supportFragmentManager,CategoryList, requireActivity())
    }

    private fun handleBottomBar() {
        binding.bottomBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_playlist -> {
                    Utils.replaceFragment(requireActivity().supportFragmentManager,LibraryFragment())
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