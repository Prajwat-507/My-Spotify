package com.example.myspotify.auth

import android.content.Intent
import android.graphics.Color
import android.hardware.lights.Light
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.os.IResultReceiver.Default
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myspotify.R
import com.example.myspotify.Utils
import com.example.myspotify.activity.UserActivity
import com.example.myspotify.databinding.FragmentSplashBinding
import com.example.myspotify.viewModel.PhoneAuthViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.reflect.Type


class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplashBinding

    private val viewModel : PhoneAuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(layoutInflater)

        handleSystemBar()


        //LoopHandler for splash screen
        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.apply {
                lifecycleScope.launch {
                    currentUser.collectLatest {
                        if (it) {
                            startActivity(Intent(requireActivity(), UserActivity::class.java))
                            requireActivity().finish()
                        } else {
                            findNavController().navigate(R.id.action_splashFragment_to_signInFragment)
                        }
                    }
                }
            }
        }, 3000)


        return binding.root
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
            }

            // Return CONSUMED if you don't want want the window insets to keep passing
            // down to descendant views.
            WindowInsetsCompat.CONSUMED
        }
    }


}