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
        binding.bottomBar.setOnItemSelectedListener { item->
            when (item.itemId) {
                R.id.action_search -> {

                    Utils.replaceFragment(requireActivity().supportFragmentManager,SearchFragment())
                    true
                }
                R.id.action_home -> {

                    Utils.replaceFragment(requireActivity().supportFragmentManager,HomeFragment())
                    true
                }
                R.id.action_playlist -> {

                    Utils.replaceFragment(requireActivity().supportFragmentManager,LibraryFragment())
                    true
                }
                else -> false
            }
        }
    }
}