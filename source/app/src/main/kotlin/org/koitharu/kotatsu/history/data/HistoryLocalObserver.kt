package org.koitharu.kotatsu.tv.history.data

import dagger.Reusable
import org.koitharu.kotatsu.tv.core.db.MangaDatabase
import org.koitharu.kotatsu.tv.core.db.entity.toManga
import org.koitharu.kotatsu.tv.core.db.entity.toMangaTags
import org.koitharu.kotatsu.tv.history.domain.model.MangaWithHistory
import org.koitharu.kotatsu.tv.list.domain.ListFilterOption
import org.koitharu.kotatsu.tv.list.domain.ListSortOrder
import org.koitharu.kotatsu.tv.local.data.index.LocalMangaIndex
import org.koitharu.kotatsu.tv.local.domain.LocalObserveMapper
import org.koitharu.kotatsu.tv.parsers.model.Manga
import javax.inject.Inject

@Reusable
class HistoryLocalObserver @Inject constructor(
	localMangaIndex: LocalMangaIndex,
	private val db: MangaDatabase,
) : LocalObserveMapper<HistoryWithManga, MangaWithHistory>(localMangaIndex) {

	fun observeAll(
		order: ListSortOrder,
		filterOptions: Set<ListFilterOption>,
		limit: Int
	) = db.getHistoryDao().observeAll(order, filterOptions, limit).mapToLocal()

	override fun toManga(e: HistoryWithManga) = e.manga.toManga(e.tags.toMangaTags(), null)

	override fun toResult(e: HistoryWithManga, manga: Manga) = MangaWithHistory(
		manga = manga,
		history = e.history.toMangaHistory(),
	)
}
