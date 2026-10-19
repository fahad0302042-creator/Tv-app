package org.koitharu.kotatsu.tv.search.domain

import org.koitharu.kotatsu.tv.parsers.model.Manga
import org.koitharu.kotatsu.tv.parsers.model.MangaListFilter
import org.koitharu.kotatsu.tv.parsers.model.SortOrder

data class SearchResults(
	val listFilter: MangaListFilter,
	val sortOrder: SortOrder,
	val manga: List<Manga>,
)
