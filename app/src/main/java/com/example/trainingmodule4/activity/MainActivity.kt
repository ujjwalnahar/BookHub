package com.example.trainingmodule4.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.trainingmodule4.R
import com.example.trainingmodule4.fragement.Dashboard
import com.example.trainingmodule4.fragement.Favourites
import com.example.trainingmodule4.fragement.aboutapp
import com.example.trainingmodule4.fragement.profiles
import com.google.android.material.navigation.NavigationView


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    lateinit var drawer:DrawerLayout
    lateinit var coordinatorly:CoordinatorLayout
    lateinit var framely:FrameLayout
    lateinit var navigationDrawer:NavigationView
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    var previousMenuItem:MenuItem?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawer=findViewById(R.id.drawer)
        coordinatorly=findViewById(R.id.coordinatorly)
        framely=findViewById(R.id.framely)
        navigationDrawer=findViewById(R.id.navigationdrawer)
        toolbar=findViewById(R.id.toolbar)
        setUpToolbar()
        openDashboard()
        val actionBarDrawerToggle=ActionBarDrawerToggle(this@MainActivity,drawer,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawer.setDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
    }
    fun setUpToolbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.title="Toolbar Title"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navigationDrawer.setNavigationItemSelectedListener {
            if(previousMenuItem!=null){
                previousMenuItem?.isChecked=false
            }
            it.isCheckable=true
            it.isChecked=true
            previousMenuItem=it
            when(it.itemId){
                R.id.dashboard ->{
                supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.framely,
                        Dashboard()
                    )

                    .commit()

                    supportActionBar?.title="DashBoard"
                    drawer.closeDrawers()
                }
                R.id.favourites ->{
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.framely,
                            Favourites()
                        )

                        .commit()
                    supportActionBar?.title="Favourties"
                    drawer.closeDrawers()
                }
                R.id.AboutApp ->{
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.framely,
                            aboutapp()
                        )

                        .commit()

                    supportActionBar?.title="AboutApp"
                    drawer.closeDrawers()
                }
                R.id.Profile ->{
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.framely,
                            profiles()
                        )

                        .commit()

                    supportActionBar?.title="Profiles"
                    drawer.closeDrawers()
                }
            }
            return@setNavigationItemSelectedListener true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id=item.itemId
        if(id==android.R.id.home){
            drawer.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }
    fun openDashboard()
    {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.framely,
                Dashboard()
            )
            .addToBackStack("Dashboard")
            .commit()

        supportActionBar?.title="DashBoard"
        navigationDrawer.setCheckedItem(R.id.dashboard)
    }

    override fun onBackPressed() {
        val frag=supportFragmentManager.findFragmentById(R.id.framely)
        when(frag){
            !is Dashboard -> openDashboard()
            else->super.onBackPressed()

        }
    }
}
