package org.koitharu.kotatsu.core.ui.tv

import android.app.UiModeManager
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koitharu.kotatsu.core.prefs.AppSettings
import org.koitharu.kotatsu.core.prefs.TvModeOverride
import org.koitharu.kotatsu.core.prefs.observeAsFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Central place deciding whether the TV (D-pad / 10-foot) adaptations are active.
 *
 * The decision combines the hardware the app is running on with the user preference
 * [AppSettings.tvModeOverride]. Everything TV-specific in the app goes through [isEnabled]
 * or [observeIsEnabled] so the whole adaptation can be turned off at runtime.
 */
@Singleton
class TvController @Inject constructor(
	@ApplicationContext context: Context,
	private val settings: AppSettings,
) {

	/**
	 * `true` when the process is running on a television-like device
	 * (Android TV / Google TV / Fire TV / emulators in TV mode).
	 */
	val isTvDevice: Boolean = detectTvDevice(context)

	val isFocusHighlightEnabled: Boolean
		get() = isEnabled && settings.isTvFocusHighlightEnabled

	val isHoverToFocusEnabled: Boolean
		get() = isEnabled && settings.isTvHoverToFocusEnabled

	val isWheelPagingEnabled: Boolean
		get() = isEnabled && settings.isTvWheelPagingEnabled

	val isAutoscrollRemoteKeysEnabled: Boolean
		get() = isEnabled && settings.isTvAutoscrollRemoteKeysEnabled

	val isEnabled: Boolean
		get() = resolve(settings.tvModeOverride)

	fun observeIsEnabled(): Flow<Boolean> = settings.observeAsFlow(AppSettings.KEY_TV_MODE) {
		tvModeOverride
	}.map(::resolve)

	fun observeIsFocusHighlightEnabled(): Flow<Boolean> = settings.observeAsFlow(
		AppSettings.KEY_TV_FOCUS_HIGHLIGHT,
	) { isTvFocusHighlightEnabled }.map { isEnabled && it }

	private fun resolve(mode: TvModeOverride): Boolean = when (mode) {
		TvModeOverride.AUTO -> isTvDevice
		TvModeOverride.ON -> true
		TvModeOverride.OFF -> false
	}

	companion object {

		fun detectTvDevice(context: Context): Boolean {
			val uiModeManager = context.getSystemService(Context.UI_MODE_SERVICE) as? UiModeManager
			if (uiModeManager?.currentModeType == Configuration.UI_MODE_TYPE_TELEVISION) {
				return true
			}
			if (context.resources.configuration.uiMode and Configuration.UI_MODE_TYPE_MASK ==
				Configuration.UI_MODE_TYPE_TELEVISION
			) {
				return true
			}
			return context.packageManager.hasSystemFeature(PackageManager.FEATURE_LEANBACK)
		}
	}
}
