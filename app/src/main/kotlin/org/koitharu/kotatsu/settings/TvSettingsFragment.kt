package org.koitharu.kotatsu.settings

import android.os.Bundle
import androidx.preference.ListPreference
import dagger.hilt.android.AndroidEntryPoint
import org.koitharu.kotatsu.R
import org.koitharu.kotatsu.core.prefs.AppSettings
import org.koitharu.kotatsu.core.prefs.TvModeOverride
import org.koitharu.kotatsu.core.ui.BasePreferenceFragment
import org.koitharu.kotatsu.core.ui.tv.TvController
import org.koitharu.kotatsu.core.util.ext.setDefaultValueCompat
import org.koitharu.kotatsu.parsers.util.names
import javax.inject.Inject

@AndroidEntryPoint
class TvSettingsFragment : BasePreferenceFragment(R.string.pref_tv) {

	@Inject
	lateinit var tvController: TvController

	override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
		addPreferencesFromResource(R.xml.pref_tv)
		findPreference<ListPreference>(AppSettings.KEY_TV_MODE)?.run {
			entryValues = TvModeOverride.entries.names()
			setDefaultValueCompat(TvModeOverride.AUTO.name)
			summary = buildSummary(value)
			setOnPreferenceChangeListener { _, newValue ->
				summary = buildSummary(newValue as? String)
				true
			}
		}
	}

	private fun buildSummary(rawValue: String?): String {
		val mode = TvModeOverride.entries.find { it.name == rawValue } ?: TvModeOverride.AUTO
		val detected = getString(
			if (tvController.isTvDevice) {
				R.string.tv_device_detected_yes
			} else {
				R.string.tv_device_detected_no
			},
		)
		return getString(R.string.tv_mode_summary_pattern, getString(modeTitle(mode)), detected)
	}

	private fun modeTitle(mode: TvModeOverride): Int = when (mode) {
		TvModeOverride.AUTO -> R.string.tv_mode_auto
		TvModeOverride.ON -> R.string.tv_mode_on
		TvModeOverride.OFF -> R.string.tv_mode_off
	}
}
