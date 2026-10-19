package org.koitharu.kotatsu.tv.list.ui.model

import org.koitharu.kotatsu.tv.core.ui.widgets.ChipsView
import org.koitharu.kotatsu.tv.list.ui.ListModelDiffCallback

data class QuickFilter(
	val items: List<ChipsView.ChipModel>,
) : ListModel {

	override fun areItemsTheSame(other: ListModel): Boolean = other is QuickFilter

	override fun getChangePayload(previousState: ListModel) = ListModelDiffCallback.PAYLOAD_NESTED_LIST_CHANGED
}
