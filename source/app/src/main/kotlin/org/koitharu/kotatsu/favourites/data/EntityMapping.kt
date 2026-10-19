package org.koitharu.kotatsu.tv.favourites.data

import org.koitharu.kotatsu.tv.core.db.entity.toManga
import org.koitharu.kotatsu.tv.core.db.entity.toMangaTags
import org.koitharu.kotatsu.tv.core.model.FavouriteCategory
import org.koitharu.kotatsu.tv.list.domain.ListSortOrder
import java.time.Instant

fun FavouriteCategoryEntity.toFavouriteCategory(id: Long = categoryId.toLong()) = FavouriteCategory(
	id = id,
	title = title,
	sortKey = sortKey,
	order = ListSortOrder(order, ListSortOrder.NEWEST),
	createdAt = Instant.ofEpochMilli(createdAt),
	isTrackingEnabled = track,
	isVisibleInLibrary = isVisibleInLibrary,
)

fun FavouriteManga.toManga() = manga.toManga(tags.toMangaTags(), null)

fun Collection<FavouriteManga>.toMangaList() = map { it.toManga() }
