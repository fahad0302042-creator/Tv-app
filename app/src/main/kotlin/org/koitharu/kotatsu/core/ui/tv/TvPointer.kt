package org.koitharu.kotatsu.core.ui.tv

import android.annotation.SuppressLint
import android.os.Build
import android.view.MotionEvent
import android.view.PointerIcon
import android.view.View

/**
 * Mouse / trackpad ("air mouse") support for TV.
 *
 * Android TV boxes ship the system pointer, but a vanilla Android app feels broken with a mouse:
 * nothing reacts to hovering and lists cannot be scrolled with the wheel. This object
 *
 *  - makes the pointer useful by assigning sensible system pointer icons,
 *  - moves the D-pad focus under the mouse cursor while hovering, so the focus ring and the
 *    pointer never disagree about what will be activated on click,
 *  - provides wheel (AXIS_V_SCROLL) helpers used by the readers for paging.
 */
object TvPointer {

	const val ICON_DEFAULT = PointerIcon.TYPE_DEFAULT
	const val ICON_HAND = PointerIcon.TYPE_HAND

	@SuppressLint("ClickableViewAccessibility")
	fun installHoverToFocus(view: View, controller: TvController) {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
			return
		}
		view.setOnHoverListener { v, event ->
			if (!controller.isHoverToFocusEnabled) {
				return@setOnHoverListener false
			}
			when (event.actionMasked) {
				MotionEvent.ACTION_HOVER_ENTER, MotionEvent.ACTION_HOVER_MOVE -> {
					if (v.isEnabled && !v.hasFocus()) {
						v.requestFocus()
					}
					true
				}

				else -> false
			}
		}
	}

	fun applyIcon(view: View, type: Int = ICON_HAND) {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
			return
		}
		view.pointerIcon = PointerIcon.getSystemIcon(view.context, type)
	}

	fun clearIcon(view: View) {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
			return
		}
		view.pointerIcon = null
	}

	/**
	 * Vertical scroll wheel delta of a generic motion event, or `0f` when the event
	 * is not a wheel scroll.
	 */
	fun verticalScrollDelta(event: MotionEvent): Float {
		if (event.actionMasked != MotionEvent.ACTION_SCROLL) {
			return 0f
		}
		return event.getAxisValue(MotionEvent.AXIS_V_SCROLL)
	}
}
