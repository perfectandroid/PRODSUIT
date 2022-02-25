package com.perfect.prodsuit.View.Activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.perfect.prodsuit.R

class HomeActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private var drawer_layout: DrawerLayout? = null
    private var nav_view: NavigationView? = null
    private var btn_menu: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homemain)
        setRegViews()
    }

    private fun setRegViews() {
        drawer_layout = findViewById<DrawerLayout>(R.id.drawer_layout)
        nav_view = findViewById<NavigationView>(R.id.nav_view)
        btn_menu = findViewById<ImageView>(R.id.btn_menu)

        btn_menu!!.setOnClickListener(this)
        nav_view!!.setNavigationItemSelectedListener(this)
        nav_view!!.setItemIconTintList(null);

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_menu -> {
                if (drawer_layout!!.isDrawerOpen(GravityCompat.START)) {
                    drawer_layout!!.closeDrawer(GravityCompat.START)
                } else {
                    drawer_layout!!.openDrawer(GravityCompat.START)
                }
            }


        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.nav_profile -> {
            }
            R.id.nav_changempin -> {
            }
            R.id.nav_about -> {
            }
            R.id.nav_contact -> {
            }
            R.id.nav_share -> {
            }
            R.id.nav_logout -> {
            }
            R.id.nav_quit -> {
            }
        }
        drawer_layout!!.closeDrawer(GravityCompat.START)
        return true
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

}