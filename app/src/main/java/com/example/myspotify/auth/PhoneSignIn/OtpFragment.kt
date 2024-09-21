package com.example.myspotify.auth.PhoneSignIn

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myspotify.R
import com.example.myspotify.Utils
import com.example.myspotify.activity.UserActivity
import com.example.myspotify.databinding.FragmentOtpBinding
import com.example.myspotify.viewModel.PhoneAuthViewModel
import kotlinx.coroutines.launch


class OtpFragment : Fragment() {

    private lateinit var binding: FragmentOtpBinding
    private lateinit var UserNumber: String

    private val viewModel: PhoneAuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOtpBinding.inflate(layoutInflater)

        onBackButtonClicked()

        getUserNumber()

        customizeOtpBox()

        getOtp()

        onLoginButtonClicked()

        return binding.root
    }

    private fun onLoginButtonClicked(){
        binding.btnLogIn.setOnClickListener {
            Utils.showToast(requireContext(), "Signing In...")

            val editText = arrayOf(
                binding.etOtp1,
                binding.etOtp2,
                binding.etOtp3,
                binding.etOtp4,
                binding.etOtp5,
                binding.etOtp6
            )
            val otp = editText.joinToString("") { it.text.toString() }

            if (otp.length < editText.size) {
                Utils.showToast(requireContext(), "Please check your otp")
            } else {
                editText.forEach { it.text!!.clear(); it.clearFocus() }
                verifyOtp(otp)
            }
        }
    }

    private fun verifyOtp(otp: String){

        Utils.showDialog(requireContext())

        viewModel.apply {
            viewModel.signInWithPhoneAuthCredential(otp, UserNumber)

            lifecycleScope.launch {
                successFullSignIn.collect{
                    if(it) {
                        Utils.hideDialog()
                        startActivity(Intent(requireActivity(), UserActivity::class.java))
                        requireActivity().finish()
                    }
                }
            }
        }
    }

    private fun getOtp(){
       Utils.showDialog(requireContext())

        viewModel.apply {
            viewModel.sendOtp(UserNumber, requireActivity())

            lifecycleScope.launch {
                _otpSent.collect{
                    if(it){
                        Utils.hideDialog()
                    }
                }
            }
        }
    }

    private fun onBackButtonClicked(){
        binding.phonenoToolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_otpFragment_to_phoneNoFragment)
        }
    }

    private fun customizeOtpBox(){
        val editText = arrayOf(binding.etOtp1, binding.etOtp2, binding.etOtp3, binding.etOtp4, binding.etOtp5, binding.etOtp6)

//        if(editText.size>=5){
//            // Setting background tint mode with SRC_ATOP as an example
//            binding.btnLogIn.backgroundTintMode = PorterDuff.Mode.SRC_ATOP
//
//            // Set the background tint color (optional)
//            binding.btnLogIn.backgroundTintList = ColorStateList.valueOf(
//                ContextCompat.getColor(requireContext(), R.color.green)
//            )
//        }

        for(i in editText.indices){

            editText[i].addTextChangedListener(object : TextWatcher {

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(p0: Editable?) {
                    if (i < editText.size - 1) {

                        if (p0!!.length == 1) {
                            editText[i + 1].requestFocus()
                        }
                        else if (p0.length == 0) {
                            if (i > 0) {
                                editText[i - 1].requestFocus()
                            }
                        }
                    }
                }
            })
        }
    }

    private fun getUserNumber() {
        val bundle = arguments
        UserNumber = bundle?.getString("PhNum").toString()

        binding.tvUserNumber.text = UserNumber
    }

}