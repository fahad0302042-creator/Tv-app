package org.koitharu.kotatsu.tv.tracker.domain.model

import org.koitharu.kotatsu.tv.parsers.model.Manga
import java.time.Instant

data class MangaTracking(
	val manga: Manga,
	val lastChapterId: Long,
	val lastCheck: Instant?,
	val lastChapterDate: Instant?,
	val newChapters: Int,
) {

	fun isEmpty(): Boolean {
		return lastChapterId == 0L
	}
}
