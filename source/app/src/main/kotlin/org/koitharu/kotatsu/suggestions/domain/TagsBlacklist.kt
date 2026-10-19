package org.koitharu.kotatsu.tv.suggestions.domain

import org.koitharu.kotatsu.tv.parsers.model.Manga
import org.koitharu.kotatsu.tv.parsers.model.MangaTag
import org.koitharu.kotatsu.tv.parsers.util.almostEquals

class TagsBlacklist(
	private val tags: Set<String>,
	private val threshold: Float,
) {

	fun isNotEmpty() = tags.isNotEmpty()

	operator fun contains(manga: Manga): Boolean {
		if (tags.isEmpty()) {
			return false
		}
		for (mangaTag in manga.tags) {
			for (tagTitle in tags) {
				if (mangaTag.title.almostEquals(tagTitle, threshold)) {
					return true
				}
			}
		}
		return false
	}

	operator fun contains(tag: MangaTag): Boolean = tags.any {
		it.almostEquals(tag.title, threshold)
	}
}
