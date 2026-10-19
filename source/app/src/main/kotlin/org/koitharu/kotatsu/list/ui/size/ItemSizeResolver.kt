package org.koitharu.kotatsu.tv.list.ui.size

import android.view.View
import android.widget.TextView
import org.koitharu.kotatsu.tv.history.ui.util.ReadingProgressView

interface ItemSizeResolver {

	val cellWidth: Int

	fun attachToView(
		view: View,
		textView: TextView?,
		progressView: ReadingProgressView?,
	)
}
