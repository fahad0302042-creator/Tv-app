package org.koitharu.kotatsu.core.prefs

import androidx.annotation.Keep

/**
 * Whether the Android TV (10-foot / D-pad) UI adaptations should be active.
 *
 * AUTO: enabled when the app runs on a television device (UiMode/LEANBACK), disabled elsewhere.
 * ON: force the TV adaptations on (useful for set-top boxes that report as tablets).
 * OFF: force them off (useful on a TV you drive with a mouse only).
 */
@Keep
enum class TvModeOverride {

	AUTO, ON, OFF;
}
