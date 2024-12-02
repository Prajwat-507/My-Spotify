package com.example.myspotify.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import com.example.myspotify.R
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class UserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        setStatusNavBar()
    }



//    private fun handleSystemBar() {
//        ViewCompat.setOnApplyWindowInsetsListener(layout) { v, windowInsets ->
//            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
//            // Apply the insets as a margin to the view. This solution sets
//            // only the bottom, left, and right dimensions, but you can apply whichever
//            // insets are appropriate to your layout. You can also update the view padding
//            // if that's more appropriate.
//            v.updateLayoutParams<ViewGroup.MarginLayoutParams> {
//                leftMargin = insets.left
//                rightMargin = insets.right
//                topMargin = insets.top
//                bottomMargin = insets.bottom
//            }
//            // Return CONSUMED if you don't want want the window insets to keep passing
//            // down to descendant views.
//            WindowInsetsCompat.CONSUMED
//        }
//    }

    private fun setStatusNavBar(){
        // Change the status bar color
        val window = window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.bg_color)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.black)
    }
}