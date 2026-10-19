package org.koitharu.kotatsu.core.ui.tv

import android.view.View

/**
 * Implemented by activities that want to control where the initial D-pad focus lands
 * (see [TvFocus.ensureInitialFocus]).
 */
interface TvFocusTarget {

	/**
	 * The view that should receive the focus when the activity starts with nothing focused,
	 * or `null` to intentionally keep the window unfocused (e.g. the reader, where D-pad keys
	 * are handled at the activity level instead of moving focus).
	 */
	fun tvInitialFocusView(): View?
}
