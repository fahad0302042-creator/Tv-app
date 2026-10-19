package org.koitharu.kotatsu.tv.settings.tv

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import org.koitharu.kotatsu.tv.R

class TvSettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.pref_tv, rootKey)
    }
}
