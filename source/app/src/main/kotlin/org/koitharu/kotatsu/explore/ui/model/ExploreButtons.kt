package org.koitharu.kotatsu.tv.explore.ui.model

import org.koitharu.kotatsu.tv.list.ui.model.ListModel

data class ExploreButtons(
	val isRandomLoading: Boolean,
	val activePresetName: String? = null,
) : ListModel {

	override fun areItemsTheSame(other: ListModel): Boolean {
		return other is ExploreButtons
	}
}
