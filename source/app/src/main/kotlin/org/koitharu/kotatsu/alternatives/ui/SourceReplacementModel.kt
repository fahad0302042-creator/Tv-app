package org.koitharu.kotatsu.tv.alternatives.ui

import org.koitharu.kotatsu.tv.list.ui.model.ListModel
import org.koitharu.kotatsu.tv.parsers.model.MangaSource

data class SourceReplacementModel(
	val source: MangaSource?,
	val matched: Int,
	val total: Int,
	val matchPercent: Int,
	val isSelected: Boolean,
) : ListModel {

	override fun areItemsTheSame(other: ListModel): Boolean {
		return other is SourceReplacementModel && other.source?.name == source?.name
	}
}
