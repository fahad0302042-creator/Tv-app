package org.koitharu.kotatsu.core.ui.tv

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Installs the TV adaptations ([TvListFocusDelegate]) into every [androidx.recyclerview.widget.RecyclerView]
 * of every screen, including the ones created later by (nested) fragments.
 *
 * Doing this globally from one place keeps the adaptation out of the ~150 layouts and the
 * dozens of list fragments of the upstream app, and automatically covers screens added later.
 */
@Singleton
class TvActivityLifecycleCallbacks @Inject constructor(
	private val controller: TvController,
) : org.koitharu.kotatsu.core.ui.DefaultActivityLifecycleCallbacks {

	private val fragmentCallbacks = object : FragmentManager.FragmentLifecycleCallbacks() {

		override fun onFragmentViewCreated(
			fm: FragmentManager,
			f: Fragment,
			v: View,
			savedInstanceState: Bundle?,
		) {
			v.post { adapt(v) }
		}
	}

	override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
		if (!controller.isEnabled) {
			return
		}
		(activity as? FragmentActivity)?.supportFragmentManager?.registerFragmentLifecycleCallbacks(
			fragmentCallbacks,
			true,
		)
	}

	override fun onActivityResumed(activity: Activity) {
		if (!controller.isEnabled) {
			return
		}
		val root = activity.window?.decorView ?: return
		root.post { adapt(root) }
		// Delayed a bit so that lists restoring their remembered focus position win over the
		// generic "focus the first focusable view" fallback.
		root.postDelayed({ TvFocus.ensureInitialFocus(activity) }, INITIAL_FOCUS_DELAY_MS)
	}

	override fun onActivityDestroyed(activity: Activity) {
		(activity as? FragmentActivity)?.supportFragmentManager?.unregisterFragmentLifecycleCallbacks(
			fragmentCallbacks,
		)
	}

	private fun adapt(root: View) {
		if (!controller.isEnabled) {
			return
		}
		TvFocus.forEachRecyclerView(root) { recyclerView ->
			TvListFocusDelegate.install(recyclerView, controller)
		}
	}

	private companion object {

		const val INITIAL_FOCUS_DELAY_MS = 200L
	}
}
