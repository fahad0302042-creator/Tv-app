package org.koitharu.kotatsu.tv.core.ui

import android.content.Context
import android.view.KeyEvent
import android.widget.FrameLayout

/**
 * Simple TV mouse pointer overlay.
 */
class TvMousePointer(context: Context) : FrameLayout(context) {
    var isEnabled = false
        private set

    init {
        visibility = android.view.View.GONE
    }

    fun toggle() {
        isEnabled = !isEnabled
        visibility = if (isEnabled) android.view.View.VISIBLE else android.view.View.GONE
    }
}
