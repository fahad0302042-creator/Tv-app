package org.koitharu.kotatsu.core.ui.tv

import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.RecyclerView
import org.koitharu.kotatsu.R

/**
 * Makes a single [RecyclerView] behave well with a D-pad and a mouse:
 *
 *  - item views become focusable (the upstream layouts are touch-only and never are),
 *  - a visible focus ring is drawn around the focused item (see [TvFocusHighlightDecoration]),
 *  - the focus ring is repainted whenever focus moves,
 *  - hovering with a mouse moves the focus under the cursor,
 *  - the focused position is remembered and restored when the screen comes back with
 *    nothing focused (the default on Android TV after a BACK navigation).
 */
class TvListFocusDelegate private constructor(
	private val recyclerView: RecyclerView,
	private val controller: TvController,
) : RecyclerView.OnChildAttachStateChangeListener,
	ViewTreeObserver.OnGlobalFocusChangeListener,
	View.OnAttachStateChangeListener {

	private var focusedPosition: Int = RecyclerView.NO_POSITION

	override fun onChildViewAttachedToWindow(view: View) {
		if (!controller.isEnabled) {
			return
		}
		if (!view.isFocusable) {
			view.isFocusable = true
			view.isFocusableInTouchMode = false
		}
		TvPointer.applyIcon(view)
		if (controller.isHoverToFocusEnabled) {
			TvPointer.installHoverToFocus(view, controller)
		}
		restoreFocusIfNeeded(view)
	}

	override fun onChildViewDetachedFromWindow(view: View) = Unit

	override fun onGlobalFocusChanged(oldFocus: View?, newFocus: View?) {
		recyclerView.invalidate()
		if (newFocus != null) {
			val item = findItemView(newFocus)
			if (item != null) {
				focusedPosition = recyclerView.getChildAdapterPosition(item)
			}
		}
	}

	override fun onViewAttachedToWindow(v: View) {
		v.viewTreeObserver.addOnGlobalFocusChangeListener(this)
	}

	override fun onViewDetachedFromWindow(v: View) {
		v.viewTreeObserver.removeOnGlobalFocusChangeListener(this)
	}

	private fun restoreFocusIfNeeded(view: View) {
		if (focusedPosition == RecyclerView.NO_POSITION) {
			return
		}
		val position = recyclerView.getChildAdapterPosition(view)
		if (position != focusedPosition) {
			return
		}
		// Only steal the focus back when the whole window has none, otherwise we would
		// fight with the toolbar / sheets / dialogs.
		view.post {
			if (recyclerView.rootView.findFocus() == null && recyclerView.isAttachedToWindow) {
				view.requestFocus()
			}
		}
	}

	private fun findItemView(view: View): View? {
		var current: View? = view
		while (current != null && current !== recyclerView) {
			val parent = current.parent
			if (parent === recyclerView) {
				return current
			}
			current = parent as? View
		}
		return null
	}

	companion object {

		/**
		 * @return `true` when the delegate was installed by this call, `false` when the
		 * [recyclerView] was already adapted.
		 */
		fun install(recyclerView: RecyclerView, controller: TvController): Boolean {
			if (recyclerView.getTag(R.id.tag_tv_focus_delegate) != null) {
				return false
			}
			val delegate = TvListFocusDelegate(recyclerView, controller)
			recyclerView.setTag(R.id.tag_tv_focus_delegate, delegate)
			recyclerView.addOnChildAttachStateChangeListener(delegate)
			recyclerView.addOnAttachStateChangeListener(delegate)
			recyclerView.descendantFocusability = ViewGroup.FOCUS_AFTER_DESCENDANTS
			if (controller.isFocusHighlightEnabled) {
				recyclerView.addItemDecoration(TvFocusHighlightDecoration(recyclerView.context))
			}
			if (recyclerView.isAttachedToWindow) {
				delegate.onViewAttachedToWindow(recyclerView)
			}
			return true
		}

		fun isInstalled(recyclerView: RecyclerView): Boolean =
			recyclerView.getTag(R.id.tag_tv_focus_delegate) != null
	}
}
