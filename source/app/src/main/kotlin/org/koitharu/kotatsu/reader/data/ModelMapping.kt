package org.koitharu.kotatsu.tv.reader.data

import org.koitharu.kotatsu.tv.parsers.model.Manga
import org.koitharu.kotatsu.tv.parsers.model.MangaChapter

fun Manga.filterChapters(branch: String?): Manga {
	if (chapters.isNullOrEmpty()) return this
	return withChapters(chapters = chapters?.filter { it.branch == branch })
}

private fun Manga.withChapters(chapters: List<MangaChapter>?) = copy(
	chapters = chapters,
)
