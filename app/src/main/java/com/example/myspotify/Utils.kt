package com.example.myspotify

import android.R
import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater

import android.view.ViewGroup
import android.view.Window

import android.widget.ProgressBar
import android.widget.Toast

import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

import com.example.myspotify.api.ApiInterface
import com.example.myspotify.databinding.ProgressViewBinding
import com.github.ybq.android.spinkit.sprite.Sprite

import com.github.ybq.android.spinkit.style.ThreeBounce

import com.google.firebase.auth.FirebaseAuth

import nl.joery.animatedbottombar.AnimatedBottomBar
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object Utils {
    private var FirebaseAuthInstance: FirebaseAuth?=null





    private var retroFit: Retrofit?=null

    private var exoPlayer: ExoPlayer?=null
    private var currentTrack: String?=null

    private lateinit var bottomBar :AnimatedBottomBar


    // Create a Dialog
    private var dialog: Dialog?=null



    fun showDialog(context: Context) {
        val binding = ProgressViewBinding.inflate(LayoutInflater.from(context))
        val progressBar = binding.spinKit as ProgressBar
        val threeBounce: Sprite = ThreeBounce()
        progressBar.indeterminateDrawable = threeBounce

        dialog = Dialog(context)

        // Optional: Remove the title bar from the dialog
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)

        // Set the custom view (binding.root) as the content of the dialog
        dialog!!.setContentView(binding.root)

        // Optional: Make dialog non-cancelable if you don't want it to close when touching outside
        dialog!!.setCancelable(false)

        dialog!!.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

//      Optional: Set the dialog background to transparent
        dialog!!.window?.setBackgroundDrawableResource(R.color.transparent)

        // Show the dialog
        dialog!!.show()
    }

    fun hideDialog(){
        if(dialog!=null){
            dialog!!.hide()
        }
    }

    fun showToast(context: Context, message: String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun Log(tag: String, msg: String){
        Log.d(tag, msg)
    }


    fun getAuthInstance(): FirebaseAuth{
        if(FirebaseAuthInstance==null){
            FirebaseAuthInstance = FirebaseAuth.getInstance()
        }
        return FirebaseAuthInstance!!
    }

    fun getUserID(): String{
        Log.e("AuthUidDDDDDD", FirebaseAuth.getInstance().currentUser!!.uid)

        return FirebaseAuth.getInstance().currentUser!!.uid
    }


    fun ExoPlayerInstance(context: Context): ExoPlayer?{
        if(exoPlayer == null){
            exoPlayer = ExoPlayer.Builder(context).build()
        }
        return exoPlayer
    }

    fun startPlaying(context: Context, track: String){
        if(exoPlayer == null){
            exoPlayer = ExoPlayer.Builder(context).build()
        }

        if(currentTrack!=track){
            currentTrack = track
            val mediaItem = MediaItem.fromUri(currentTrack!!)
            exoPlayer!!.setMediaItem(mediaItem)
            exoPlayer!!.prepare()
            exoPlayer!!.play()
        }
    }

    fun getCurrentTrack(): String? {
        return currentTrack
    }


    fun makeApiCall(BASE_URL: String): ApiInterface {
        // Step 1: Create Retrofit instance
        if (retroFit == null) {
            retroFit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) // JSON converter
                .build()
        }

        // Step 2: Return ApiInterface created from Retrofit
        return retroFit!!.create(ApiInterface::class.java)
    }
}