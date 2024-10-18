package com.example.myspotify.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myspotify.R
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class UserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

    }
}