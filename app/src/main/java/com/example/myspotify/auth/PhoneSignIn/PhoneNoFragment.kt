package com.example.myspotify.auth.PhoneSignIn

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.navigation.fragment.findNavController
import com.example.myspotify.R
import com.example.myspotify.Utils
import com.example.myspotify.databinding.FragmentPhoneNoBinding


class PhoneNoFragment : Fragment() {
    private lateinit var binding: FragmentPhoneNoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPhoneNoBinding.inflate(layoutInflater)

        setStatusNavBar()
        handleSystemBar()

        onBackButtonClicked()

        getUserNumber()

        onNextBtnClicked()

        return binding.root
    }

    private fun onBackButtonClicked(){
        binding.phonenoToolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_phoneNoFragment_to_signInOptFragment)
        }
    }

    private fun getUserNumber(){
        binding.etPhoneNum.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0!!.length>=10) {
                    // Setting background tint mode with SRC_ATOP as an example
                    binding.btnNext.backgroundTintMode = PorterDuff.Mode.SRC_ATOP

                    // Set the background tint color (optional)
                    binding.btnNext.backgroundTintList = ColorStateList.valueOf(
                        ContextCompat.getColor(requireContext(), R.color.green)
                    )
                }
                else{
                    // Setting background tint mode with SRC_ATOP as an example
                    binding.btnNext.backgroundTintMode = PorterDuff.Mode.SRC_ATOP

                    // Set the background tint color (optional)
                    binding.btnNext.backgroundTintList = ColorStateList.valueOf(
                        ContextCompat.getColor(requireContext(), R.color.grey)
                    )
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }

    private fun onNextBtnClicked(){
        binding.btnNext.setOnClickListener {
            val userNum = binding.etPhoneNum.text.toString()

            if(userNum.trim().length==10 && userNum.trim().isNotEmpty()){
                val bundle = Bundle()
                bundle.putString("PhNum", userNum)
                findNavController().navigate(R.id.action_phoneNoFragment_to_otpFragment, bundle)
            }
            else{
                Utils.showToast(requireActivity(), "Please enter valid Phone number")
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