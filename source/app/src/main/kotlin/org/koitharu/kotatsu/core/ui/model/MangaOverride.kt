package org.koitharu.kotatsu.tv.core.ui.model

import org.koitharu.kotatsu.tv.parsers.model.ContentRating

data class MangaOverride(
	val coverUrl: String?,
	val title: String?,
	val contentRating: ContentRating?,
)
