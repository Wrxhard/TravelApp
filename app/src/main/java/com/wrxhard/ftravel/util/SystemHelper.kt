package com.wrxhard.ftravel.util

import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import eightbitlab.com.blurview.BlurView
import eightbitlab.com.blurview.RenderScriptBlur

object SystemHelper {
    @RequiresApi(Build.VERSION_CODES.R)
    fun hideSystem(activity: AppCompatActivity) {
        val window = activity.window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.attributes.layoutInDisplayCutoutMode = WindowManager
                .LayoutParams
                .LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val windowInsetsController =
            WindowCompat.getInsetsController(window, window.decorView)

        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    }

    fun blurView(activity: AppCompatActivity,rootView: ViewGroup, blurView: BlurView, radius: Float){
        val window = activity.window
        val decorView = window.decorView
        val windowBackground = decorView.background
        blurView.setupWith(rootView,  RenderScriptBlur(activity)) // or RenderEffectBlur
            .setFrameClearDrawable(windowBackground) // Optional
            .setBlurRadius(radius)
    }
}