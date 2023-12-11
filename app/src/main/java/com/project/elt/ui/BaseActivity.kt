package com.project.elt.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.project.elt.R
import com.project.elt.databinding.ActivityBaseBinding
import com.project.elt.ui.browser.BrowserActivity

open class BaseActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        print("BASE ACTIVITY")
    }

    override fun setContentView(view: View?) {
        print("BASE ACTIVITY")
        drawerLayout = layoutInflater.inflate(R.layout.activity_base, null) as DrawerLayout
        navigationView = drawerLayout.findViewById(R.id.navigationBase)
        navigationView.setNavigationItemSelectedListener(this)
        val container: FrameLayout = drawerLayout.findViewById(R.id.container)
        container.addView(view)
        super.setContentView(drawerLayout)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (item.itemId ==R.id.nav_contact){
            startActivity(Intent(this,BrowserActivity::class.java).putExtra("browserInt",1))
            return true
        }
        else if (item.itemId ==R.id.nav_privacypolicy){
            startActivity(Intent(this,BrowserActivity::class.java).putExtra("browserInt",2))
            return true
        }
        return false
    }
}