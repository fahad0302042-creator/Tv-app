package org.koitharu.kotatsu.tv.filter.ui.model

import org.koitharu.kotatsu.tv.core.ui.widgets.ChipsView
import org.koitharu.kotatsu.tv.parsers.model.SortOrder

data class FilterHeaderModel(
	val chips: Collection<ChipsView.ChipModel>,
	val sortOrder: SortOrder?,
	val isFilterApplied: Boolean,
) {

	val textSummary: String
		get() = chips.mapNotNull { if (it.isChecked) it.title else null }.joinToString()
}
