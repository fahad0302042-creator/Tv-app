package org.koitharu.kotatsu.tv.core.ui

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout

/**
 * TV mouse pointer overlay: toggle with MENU button, move with D-pad,
 * click with OK/select.
 */
class TvMousePointer(context: Context) : FrameLayout(context) {

    private val pointerView = View(context).apply {
        layoutParams = LayoutParams(48, 48)
        background = context.getDrawable(android.R.drawable.ic_menu_info_details)
    }

    var isEnabled = false
        private set

    private val handler = Handler(Looper.getMainLooper())
    private val moveStep = 30f // pixels per D-pad press
    private val metrics: DisplayMetrics = context.resources.displayMetrics
    private var pointerX = metrics.widthPixels / 2f
    private var pointerY = metrics.heightPixels / 2f

    init {
        addView(pointerView)
        pointerView.x = pointerX
        pointerView.y = pointerY
        visibility = GONE
    }

    fun toggle() {
        isEnabled = !isEnabled
        visibility = if (isEnabled) VISIBLE else GONE
    }

    fun handleKey(keyCode: Int): Boolean {
        if (!isEnabled) return false
        return when (keyCode) {
            KeyEvent.KEYCODE_DPAD_UP -> { move(0f, -moveStep); true }
            KeyEvent.KEYCODE_DPAD_DOWN -> { move(0f, moveStep); true }
            KeyEvent.KEYCODE_DPAD_LEFT -> { move(-moveStep, 0f); true }
            KeyEvent.KEYCODE_DPAD_RIGHT -> { move(moveStep, 0f); true }
            KeyEvent.KEYCODE_DPAD_CENTER, KeyEvent.KEYCODE_ENTER -> {
                // Simulate click at pointer position
                dispatchClick(pointerX + pointerView.width / 2f, pointerY + pointerView.height / 2f)
                true
            }
            KeyEvent.KEYCODE_BUTTON_SELECT, KeyEvent.KEYCODE_BUTTON_A -> {
                dispatchClick(pointerX + pointerView.width / 2f, pointerY + pointerView.height / 2f)
                true
            }
            else -> false
        }
    }

    private fun move(dx: Float, dy: Float) {
        pointerX = (pointerX + dx).coerceIn(0f, metrics.widthPixels - pointerView.width.toFloat())
        pointerY = (pointerY + dy).coerceIn(0f, metrics.heightPixels - pointerView.height.toFloat())
        pointerView.x = pointerX
        pointerView.y = pointerY
    }

    private fun dispatchClick(cx: Float, cy: Float) {
        val down = MotionEvent.obtain(
            System.currentTimeMillis(), System.currentTimeMillis(),
            MotionEvent.ACTION_DOWN, cx, cy, 0
        )
        val up = MotionEvent.obtain(
            System.currentTimeMillis(), System.currentTimeMillis(),
            MotionEvent.ACTION_UP, cx, cy, 0
        )
        rootView?.dispatchTouchEvent(down)
        rootView?.dispatchTouchEvent(up)
        down.recycle()
        up.recycle()
    }
}
