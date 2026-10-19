package org.koitharu.kotatsu.tv.favourites.domain.model

import org.koitharu.kotatsu.tv.core.model.MangaSource

data class Cover(
	val url: String?,
	val source: String,
) {
	val mangaSource by lazy { MangaSource(source) }
}
