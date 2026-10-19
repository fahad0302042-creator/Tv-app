package org.koitharu.kotatsu.tv.history.domain.model

import org.koitharu.kotatsu.tv.core.model.MangaHistory
import org.koitharu.kotatsu.tv.parsers.model.Manga

data class MangaWithHistory(
	val manga: Manga,
	val history: MangaHistory
)
