package org.koitharu.kotatsu.tv.reader.ui

import com.google.android.material.slider.LabelFormatter
import org.koitharu.kotatsu.tv.parsers.util.format

class PageLabelFormatter : LabelFormatter {

	override fun getFormattedValue(value: Float): String {
		return (value + 1).format(0)
	}
}
