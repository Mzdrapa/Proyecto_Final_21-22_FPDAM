package com.ubedaPablo.proyecto.ui

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.ubedaPablo.proyecto.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}