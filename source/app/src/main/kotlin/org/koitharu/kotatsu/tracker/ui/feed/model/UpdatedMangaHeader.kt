package org.koitharu.kotatsu.tv.tracker.ui.feed.model

import org.koitharu.kotatsu.tv.list.ui.ListModelDiffCallback
import org.koitharu.kotatsu.tv.list.ui.model.ListModel
import org.koitharu.kotatsu.tv.list.ui.model.MangaListModel

data class UpdatedMangaHeader(
	val list: List<MangaListModel>,
) : ListModel {

	override fun areItemsTheSame(other: ListModel): Boolean {
		return other is UpdatedMangaHeader
	}

	override fun getChangePayload(previousState: ListModel): Any {
		return ListModelDiffCallback.PAYLOAD_NESTED_LIST_CHANGED
	}
}
