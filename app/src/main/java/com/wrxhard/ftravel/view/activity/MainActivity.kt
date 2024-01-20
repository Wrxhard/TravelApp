package com.wrxhard.ftravel.view.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.wrxhard.ftravel.R
import com.wrxhard.ftravel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        //bindingview
        binding = ActivityMainBinding.inflate(layoutInflater)
        //hideSystemBar
        hideSystem()
        //Find and set Navigation controller
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.Screen) as NavHostFragment?
        val navController = navHostFragment?.navController
        navController?.let {
            binding.bottomNav.selectedItemId = R.id.home_nav_graph
            //setup bottom nav
            setupBottomNav(it, binding.bottomNav)
        }
        setContentView(binding.root)
    }

    //hide systembar
    @RequiresApi(Build.VERSION_CODES.R)
    private fun hideSystem(){
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

    //Set up bottom navigation
    private fun setupBottomNav(navController: NavController, bottomNavigationView: BottomNavigationView)
    {

        bottomNavigationView.apply {
            navController.let {
                    navController ->
                NavigationUI.setupWithNavController(this,navController)
                setOnItemSelectedListener {
                        item->
                        NavigationUI.onNavDestinationSelected(item, navController)
                    true
                }
                setOnItemReselectedListener {
                    val selectedItemNavGraph=navController.graph.findNode(it.itemId) as NavGraph
                    selectedItemNavGraph.let {
                            menuGraph->
                        navController.popBackStack(menuGraph.startDestinationId,false)
                    }
                }

            }
        }
    }
}