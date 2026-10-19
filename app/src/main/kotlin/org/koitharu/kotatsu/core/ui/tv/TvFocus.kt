package org.koitharu.kotatsu.core.ui.tv

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import org.koitharu.kotatsu.R

/**
 * Helpers used by [TvActivityLifecycleCallbacks] to adapt arbitrary view hierarchies
 * for D-pad / mouse usage.
 */
object TvFocus {

	/**
	 * Marks [view] (and therefore its whole subtree) as excluded from the TV list adaptation.
	 * Used by the reader, where D-pad keys must page/scroll instead of moving the focus
	 * between page views.
	 */
	fun skipSubtree(view: View) {
		view.setTag(R.id.tag_tv_adapted, Boolean.FALSE)
	}

	fun isSubtreeSkipped(view: View): Boolean =
		view.getTag(R.id.tag_tv_adapted) == Boolean.FALSE

	/**
	 * @return `true` when [recyclerView] should get the TV focus treatment.
	 */
	fun shouldAdapt(recyclerView: RecyclerView): Boolean {
		if (isSubtreeSkipped(recyclerView)) {
			return false
		}
		// ViewPager2 hosts a RecyclerView internally; moving focus between its pages
		// would fight with the swipe navigation.
		if (recyclerView.parent is ViewPager2) {
			return false
		}
		return recyclerView.layoutManager != null
	}

	fun forEachRecyclerView(root: View, action: (RecyclerView) -> Unit) {
		if (isSubtreeSkipped(root)) {
			return
		}
		if (root is RecyclerView && shouldAdapt(root)) {
			action(root)
		}
		if (root is ViewGroup) {
			for (i in 0 until root.childCount) {
				forEachRecyclerView(root.getChildAt(i), action)
			}
		}
	}

	/**
	 * First focusable view in tree order, skipping invisible subtrees.
	 */
	fun findFirstFocusable(view: View): View? {
		if (!view.isShown || !view.isEnabled) {
			return null
		}
		if (view is ViewGroup) {
			for (i in 0 until view.childCount) {
				val result = findFirstFocusable(view.getChildAt(i))
				if (result != null) {
					return result
				}
			}
		}
		return if (view.isFocusable) view else null
	}

	/**
	 * Gives the window an initial focus when it has none, which is the default (broken)
	 * state of every Android activity on a TV: without it the first D-pad presses do nothing.
	 */
	fun ensureInitialFocus(activity: Activity) {
		val root = activity.window?.decorView ?: return
		if (root.findFocus() != null) {
			return
		}
		val target = when (activity) {
			is TvFocusTarget -> activity.tvInitialFocusView()
			else -> findFirstFocusable(root)
		} ?: return
		target.requestFocus()
	}
}
