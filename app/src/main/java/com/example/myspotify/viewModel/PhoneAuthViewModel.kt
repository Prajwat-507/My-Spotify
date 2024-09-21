package com.example.myspotify.viewModel

import android.app.Activity
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.myspotify.Utils
import com.example.myspotify.models.User
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.concurrent.TimeUnit

class PhoneAuthViewModel: ViewModel() {

    private val _otpVerification = MutableStateFlow<String?>(null)
    val _otpSent = MutableStateFlow(false)

    val _successFullSignIn = MutableStateFlow(false)
    val successFullSignIn = _successFullSignIn

    private val _currentUser = MutableStateFlow(false)
    val currentUser = _currentUser

     init {
        Utils.getAuthInstance().currentUser?.let {
            _currentUser.value = true
        }
    }

    fun sendOtp(userNumber: String, activity: Activity){

        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {}

            override fun onVerificationFailed(e: FirebaseException) {}

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken,
            ) {
                _otpVerification.value = verificationId
                _otpSent.value = true
            }
        }

        val options = PhoneAuthOptions.newBuilder(Utils.getAuthInstance())
            .setPhoneNumber("+91$userNumber") // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(activity) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun signInWithPhoneAuthCredential(otp: String, userNumber: String) {

        val credential = PhoneAuthProvider.getCredential(_otpVerification.value.toString(), otp)

        Utils.getAuthInstance().signInWithCredential(credential)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = User(Utils.getUserID(), userNumber , null)
                    FirebaseDatabase.getInstance().getReference("All Users").child("User").child(user.uid!!).setValue(user)
                    successFullSignIn.value = true
                }
                else {

                }
            }
    }
}