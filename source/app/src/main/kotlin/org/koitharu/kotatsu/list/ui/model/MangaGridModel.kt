package org.koitharu.kotatsu.tv.list.ui.model

import org.koitharu.kotatsu.tv.core.ui.model.MangaOverride
import org.koitharu.kotatsu.tv.list.domain.ReadingProgress
import org.koitharu.kotatsu.tv.list.ui.ListModelDiffCallback.Companion.PAYLOAD_ANYTHING_CHANGED
import org.koitharu.kotatsu.tv.list.ui.ListModelDiffCallback.Companion.PAYLOAD_PROGRESS_CHANGED
import org.koitharu.kotatsu.tv.parsers.model.Manga

data class MangaGridModel(
	override val manga: Manga,
	override val override: MangaOverride?,
	override val counter: Int,
	val progress: ReadingProgress?,
	val isFavorite: Boolean,
	val isSaved: Boolean,
	val isPinned: Boolean = false,
) : MangaListModel() {

	override fun getChangePayload(previousState: ListModel): Any? = when {
		previousState !is MangaGridModel || previousState.manga != manga -> null

		previousState.progress != progress -> PAYLOAD_PROGRESS_CHANGED
		previousState.isFavorite != isFavorite ||
			previousState.isSaved != isSaved ||
			previousState.isPinned != isPinned -> PAYLOAD_ANYTHING_CHANGED

		else -> super.getChangePayload(previousState)
	}
}
