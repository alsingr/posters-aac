package com.tests.alsingr.posters.ui.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import com.tests.alsingr.posters.R


import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.tests.alsingr.posters.databinding.PostersActivityBinding

class PostersActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: PostersActivityBinding = DataBindingUtil.setContentView(this,
            R.layout.posters_activity)
        drawerLayout = binding.drawerLayout

        // Set up ActionBar
        setSupportActionBar(binding.toolbar)

    }

    override fun onSupportNavigateUp() = findNavController(R.id.posters_nav_fragment).navigateUp()

}

