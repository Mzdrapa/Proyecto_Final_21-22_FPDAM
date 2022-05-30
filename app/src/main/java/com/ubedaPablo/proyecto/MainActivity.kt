package com.ubedaPablo.proyecto

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.bundleOf
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.google.android.material.navigation.NavigationView
import com.ubedaPablo.proyecto.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var preferences: SharedPreferences

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase!!, "es"))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_dice, R.id.nav_character, R.id.nav_rules
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        preferences = PreferenceManager.getDefaultSharedPreferences(this)
        preferences.registerOnSharedPreferenceChangeListener(this)

        if (preferences.getBoolean("FirstOpen", true)){
            findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.infoFragment, bundleOf(
                Pair("FirstOpen", true)
            ))
            preferences.edit().putBoolean("FirstOpen", false).apply()
        }

        loadSettings()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        menu.findItem(R.id.add_character).isVisible = false
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_settings -> {
            findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.settingsFragment)
            true
        }
        R.id.action_contact -> {
            findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.infoFragment, bundleOf(Pair("FirstOpen", false)))
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        loadSettings()
    }

    private fun loadSettings() {
        //Light/Dark Modes
        val mode = if (preferences.getBoolean(
                "light_dark",
                false
            )
        ) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        AppCompatDelegate.setDefaultNightMode(mode)

        //Locales
        val localeBeforeChange = Locale.getDefault().language
        val lang = preferences.getString("list_language", Locale.getDefault().language)
        LocaleHelper.setLocale(baseContext, lang)
        if (!Locale.getDefault().language.equals(localeBeforeChange)) {
            recreate()
        }
    }

}