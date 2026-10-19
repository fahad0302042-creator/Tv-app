package org.koitharu.kotatsu.reader.ui

import android.content.res.Resources
import android.view.KeyEvent
import android.view.View
import org.koitharu.kotatsu.R
import org.koitharu.kotatsu.core.prefs.AppSettings
import org.koitharu.kotatsu.core.prefs.ReaderMode
import org.koitharu.kotatsu.reader.data.TapGridSettings
import org.koitharu.kotatsu.reader.domain.TapGridArea
import org.koitharu.kotatsu.core.ui.tv.TvController
import org.koitharu.kotatsu.reader.ui.tapgrid.TapAction
import kotlin.math.sign

class ReaderControlDelegate(
	resources: Resources,
	private val settings: AppSettings,
	private val tapGridSettings: TapGridSettings,
	private val tvController: TvController,
	private val listener: OnInteractionListener,
) : View.OnClickListener {

	private var minScrollDelta = resources.getDimensionPixelSize(R.dimen.reader_scroll_delta_min)

	// DPAD_CENTER state machine: short press toggles the UI, long press toggles auto-scroll.
	private var isCenterPending = false
	private var isCenterLongHandled = false

	override fun onClick(v: View) {
		when (v.id) {
			R.id.button_prev -> listener.switchChapterBy(-1)
			R.id.button_next -> listener.switchChapterBy(1)
		}
	}

	fun onGridTouch(area: TapGridArea): Boolean {
		val action = tapGridSettings.getTapAction(
			area = area,
			isLongTap = false,
		) ?: return false
		processAction(action)
		return true
	}

	fun onGridLongTouch(area: TapGridArea) {
		val action = tapGridSettings.getTapAction(
			area = area,
			isLongTap = true,
		) ?: return
		processAction(action)
	}

	fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
		// When the focus is inside the reader controls (toolbar, docked actions bar,
		// auto-scroll panel) the D-pad must move the focus / activate the focused control
		// instead of paging. Returning false lets the framework do the focus traversal.
		if (listener.isUiControlsFocused() && isNavigationKey(keyCode)) {
			return false
		}
		when (keyCode) {
			KeyEvent.KEYCODE_NAVIGATE_NEXT,
			KeyEvent.KEYCODE_SPACE -> switchBy(1, event, false)

			KeyEvent.KEYCODE_PAGE_DOWN -> switchBy(1, event, false)

			KeyEvent.KEYCODE_NAVIGATE_PREVIOUS -> switchBy(-1, event, false)
			KeyEvent.KEYCODE_PAGE_UP -> switchBy(-1, event, false)

			KeyEvent.KEYCODE_R -> switchBy(1, null, false)

			KeyEvent.KEYCODE_L -> switchBy(-1, null, false)

			KeyEvent.KEYCODE_VOLUME_UP -> if (settings.isReaderVolumeButtonsEnabled) {
				switchBy(if (settings.isReaderNavigationInverted) 1 else -1, event, false)
			} else {
				return false
			}

			KeyEvent.KEYCODE_VOLUME_DOWN -> if (settings.isReaderVolumeButtonsEnabled) {
				switchBy(if (settings.isReaderNavigationInverted) -1 else 1, event, false)
			} else {
				return false
			}

			KeyEvent.KEYCODE_DPAD_RIGHT -> switchByRelative(if (settings.isReaderNavigationInverted) -1 else 1, event)

			KeyEvent.KEYCODE_DPAD_LEFT -> switchByRelative(if (settings.isReaderNavigationInverted) 1 else -1, event)

			KeyEvent.KEYCODE_DPAD_CENTER -> return onCenterKeyDown(event)

			KeyEvent.KEYCODE_SYSTEM_NAVIGATION_UP,
			KeyEvent.KEYCODE_DPAD_UP -> switchBy(if (settings.isReaderNavigationInverted) 1 else -1, event, true)

			KeyEvent.KEYCODE_SYSTEM_NAVIGATION_DOWN,
			KeyEvent.KEYCODE_DPAD_DOWN -> switchBy(if (settings.isReaderNavigationInverted) -1 else 1, event, true)

			//region TV remote (TV fork)
			KeyEvent.KEYCODE_MENU,
			KeyEvent.KEYCODE_BUTTON_Y -> listener.openMenu()

			KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE,
			KeyEvent.KEYCODE_MEDIA_PLAY -> if (isAutoscrollKeysEnabled()) {
				listener.onScrollTimerClick(isLongClick = true)
			} else {
				return false
			}

			KeyEvent.KEYCODE_MEDIA_PAUSE -> if (isAutoscrollKeysEnabled() && listener.isAutoscrollActive()) {
				listener.onScrollTimerClick(isLongClick = true)
			} else {
				return false
			}

			KeyEvent.KEYCODE_MEDIA_FAST_FORWARD,
			KeyEvent.KEYCODE_MEDIA_NEXT -> if (isAutoscrollKeysEnabled()) {
				listener.onAutoscrollSpeedStep(SPEED_STEP)
			} else {
				return false
			}

			KeyEvent.KEYCODE_MEDIA_REWIND,
			KeyEvent.KEYCODE_MEDIA_PREVIOUS -> if (isAutoscrollKeysEnabled()) {
				listener.onAutoscrollSpeedStep(-SPEED_STEP)
			} else {
				return false
			}
			//endregion

			else -> return false
		}
		return true
	}

	fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
		when (keyCode) {
			KeyEvent.KEYCODE_DPAD_CENTER -> {
				val wasPending = isCenterPending
				isCenterPending = false
				if (isCenterLongHandled) {
					isCenterLongHandled = false
					return true
				}
				if (wasPending && !listener.isUiControlsFocused()) {
					listener.toggleUiVisibility()
					return true
				}
				return false
			}

			KeyEvent.KEYCODE_VOLUME_DOWN,
			KeyEvent.KEYCODE_VOLUME_UP -> return settings.isReaderVolumeButtonsEnabled

			else -> return false
		}
	}

	private fun onCenterKeyDown(event: KeyEvent?): Boolean {
		val repeatCount = event?.repeatCount ?: 0
		if (listener.isUiControlsFocused()) {
			// Let the focused control handle the press itself.
			return false
		}
		if (repeatCount == 0) {
			isCenterPending = true
			isCenterLongHandled = false
			return true
		}
		if (isCenterPending && !isCenterLongHandled) {
			isCenterPending = false
			isCenterLongHandled = true
			listener.onScrollTimerClick(isLongClick = true)
		}
		return true
	}

	private fun isNavigationKey(keyCode: Int): Boolean = when (keyCode) {
		KeyEvent.KEYCODE_DPAD_UP,
		KeyEvent.KEYCODE_DPAD_DOWN,
		KeyEvent.KEYCODE_DPAD_LEFT,
		KeyEvent.KEYCODE_DPAD_RIGHT,
		KeyEvent.KEYCODE_DPAD_CENTER,
		KeyEvent.KEYCODE_ENTER,
		KeyEvent.KEYCODE_NUMPAD_ENTER,
		KeyEvent.KEYCODE_SPACE -> true

		else -> false
	}

	private fun isAutoscrollKeysEnabled(): Boolean = tvController.isAutoscrollRemoteKeysEnabled

	private fun processAction(action: TapAction) {
		when (action) {
			TapAction.PAGE_NEXT -> listener.switchPageBy(1)
			TapAction.PAGE_PREV -> listener.switchPageBy(-1)
			TapAction.CHAPTER_NEXT -> listener.switchChapterBy(1)
			TapAction.CHAPTER_PREV -> listener.switchChapterBy(-1)
			TapAction.TOGGLE_UI -> listener.toggleUiVisibility()
			TapAction.SHOW_MENU -> listener.openMenu()
		}
	}

	private fun isReaderTapsReversed(): Boolean {
		return settings.isReaderControlAlwaysLTR && listener.readerMode == ReaderMode.REVERSED
	}

	private fun switchBy(delta: Int, event: KeyEvent?, scroll: Boolean) {
		if (event?.isCtrlPressed == true) {
			listener.switchChapterBy(delta)
		} else if (scroll) {
			if (!listener.scrollBy(minScrollDelta * delta.sign, smooth = true)) {
				listener.switchPageBy(delta)
			}
		} else {
			listener.switchPageBy(delta)
		}
	}

	private fun switchByRelative(delta: Int, event: KeyEvent?) {
		return switchBy(if (isReaderTapsReversed()) -delta else delta, event, scroll = false)
	}

	interface OnInteractionListener {

		val readerMode: ReaderMode?

		fun switchPageBy(delta: Int)

		fun switchPageTo(index: Int)

		fun switchChapterBy(delta: Int)

		fun scrollBy(delta: Int, smooth: Boolean): Boolean

		fun toggleUiVisibility()

		fun onBookmarkClick()

		fun openMenu()

		fun onSavePageClick()

		fun onScrollTimerClick(isLongClick: Boolean)

		fun toggleScreenOrientation()

		fun isReaderResumed(): Boolean

		/**
		 * `true` when the D-pad focus is currently inside the reader UI (toolbar, docked
		 * actions bar or the auto-scroll panel), so keys should control the UI instead of
		 * the pages. (TV fork)
		 */
		fun isUiControlsFocused(): Boolean

		fun isAutoscrollActive(): Boolean

		fun onAutoscrollSpeedStep(delta: Float)
	}

	private companion object {

		const val SPEED_STEP = 0.05f
	}
}
