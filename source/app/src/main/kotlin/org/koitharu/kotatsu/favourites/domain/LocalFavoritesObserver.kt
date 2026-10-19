package org.koitharu.kotatsu.tv.favourites.domain

import dagger.Reusable
import kotlinx.coroutines.flow.Flow
import org.koitharu.kotatsu.tv.core.db.MangaDatabase
import org.koitharu.kotatsu.tv.core.db.entity.toManga
import org.koitharu.kotatsu.tv.core.db.entity.toMangaTags
import org.koitharu.kotatsu.tv.favourites.data.FavouriteManga
import org.koitharu.kotatsu.tv.list.domain.ListFilterOption
import org.koitharu.kotatsu.tv.list.domain.ListSortOrder
import org.koitharu.kotatsu.tv.local.data.index.LocalMangaIndex
import org.koitharu.kotatsu.tv.local.domain.LocalObserveMapper
import org.koitharu.kotatsu.tv.parsers.model.Manga
import javax.inject.Inject

@Reusable
class LocalFavoritesObserver @Inject constructor(
	localMangaIndex: LocalMangaIndex,
	private val db: MangaDatabase,
) : LocalObserveMapper<FavouriteManga, Manga>(localMangaIndex) {

	fun observeAll(
		order: ListSortOrder,
		filterOptions: Set<ListFilterOption>,
		limit: Int
	): Flow<List<Manga>> = db.getFavouritesDao().observeAll(order, filterOptions, limit).mapToLocal()

	fun observeAll(
		categoryId: Long,
		order: ListSortOrder,
		filterOptions: Set<ListFilterOption>,
		limit: Int
	): Flow<List<Manga>> = db.getFavouritesDao().observeAll(categoryId, order, filterOptions, limit).mapToLocal()

	override fun toManga(e: FavouriteManga) = e.manga.toManga(e.tags.toMangaTags(), null)

	override fun toResult(e: FavouriteManga, manga: Manga) = manga
}
