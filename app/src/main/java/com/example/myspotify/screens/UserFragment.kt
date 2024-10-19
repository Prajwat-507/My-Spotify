package com.example.myspotify.screens

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myspotify.R
import com.example.myspotify.Utils
import com.example.myspotify.activity.MainActivity
import com.example.myspotify.activity.UserActivity
import com.example.myspotify.databinding.FragmentUserBinding
import com.example.myspotify.viewModel.PhoneAuthViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import nl.joery.animatedbottombar.AnimatedBottomBar


class UserFragment : Fragment() {
    private lateinit var binding: FragmentUserBinding

    private val viewModel : PhoneAuthViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentUserBinding.inflate(layoutInflater)

        handleBottomBar()

        setUserNum()

        signOutUser()

        return binding.root
    }

    private fun signOutUser() {
        binding.btnSignOut.setOnClickListener {
            viewModel.apply {
                lifecycleScope.launch {
                    currentUser.collectLatest {
                        if(it){
                            Utils.getAuthInstance().signOut()
                            startActivity(Intent(requireActivity(), MainActivity::class.java))
                            requireActivity().finish()
                        }
                    }
                }
            }
        }
    }

    private fun setUserNum() {
        val userInstance = Utils.getAuthInstance()
        binding.tvUserPhNumber.setText("+91 ${userInstance.currentUser!!.phoneNumber}")
    }

    private fun handleBottomBar() {
        binding.bottomBar.setOnTabSelectListener(object : AnimatedBottomBar.OnTabSelectListener{

            override fun onTabSelected(lastIndex: Int, lastTab: AnimatedBottomBar.Tab?,
                                       newIndex: Int, newTab: AnimatedBottomBar.Tab
            ) {
                if(newTab.title.equals("home")){
                    findNavController().navigate(R.id.action_userFragment_to_homeFragment)

                }
                if(newTab.title.equals("library")){
                    findNavController().navigate(R.id.action_userFragment_to_playListFragment)
                }
                if(newTab.title.equals("search")){
                    findNavController().navigate(R.id.action_userFragment_to_searchFragment)
                }

            }

            override fun onTabReselected(index: Int, tab: AnimatedBottomBar.Tab) {
                Log.d("bottom_bar", "Reselected index: $index, title: ${tab.title}")
            }
        })
    }
}