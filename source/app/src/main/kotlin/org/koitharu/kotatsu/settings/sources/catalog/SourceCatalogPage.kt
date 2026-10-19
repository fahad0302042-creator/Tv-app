package org.koitharu.kotatsu.tv.settings.sources.catalog

import org.koitharu.kotatsu.tv.list.ui.ListModelDiffCallback
import org.koitharu.kotatsu.tv.list.ui.model.ListModel
import org.koitharu.kotatsu.tv.parsers.model.ContentType

data class SourceCatalogPage(
	val type: ContentType,
	val items: List<SourceCatalogItem>,
) : ListModel {

	override fun areItemsTheSame(other: ListModel): Boolean {
		return other is SourceCatalogPage && other.type == type
	}

	override fun getChangePayload(previousState: ListModel): Any {
		return ListModelDiffCallback.PAYLOAD_NESTED_LIST_CHANGED
	}
}
