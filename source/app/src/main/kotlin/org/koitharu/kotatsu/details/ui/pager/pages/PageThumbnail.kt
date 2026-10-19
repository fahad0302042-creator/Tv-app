package org.koitharu.kotatsu.tv.details.ui.pager.pages

import org.koitharu.kotatsu.tv.list.ui.model.ListModel
import org.koitharu.kotatsu.tv.reader.ui.pager.ReaderPage

data class PageThumbnail(
	val isCurrent: Boolean,
	val page: ReaderPage,
) : ListModel {

	val number
		get() = page.index + 1

	override fun areItemsTheSame(other: ListModel): Boolean {
		return other is PageThumbnail && page == other.page
	}
}
