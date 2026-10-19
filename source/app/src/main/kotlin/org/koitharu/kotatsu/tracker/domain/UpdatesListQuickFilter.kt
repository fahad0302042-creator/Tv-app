package org.koitharu.kotatsu.tv.tracker.domain

import org.koitharu.kotatsu.tv.core.prefs.AppSettings
import org.koitharu.kotatsu.tv.favourites.domain.FavouritesRepository
import org.koitharu.kotatsu.tv.list.domain.ListFilterOption
import org.koitharu.kotatsu.tv.list.domain.MangaListQuickFilter
import javax.inject.Inject

class UpdatesListQuickFilter @Inject constructor(
	private val favouritesRepository: FavouritesRepository,
	settings: AppSettings,
) : MangaListQuickFilter(settings) {

	override suspend fun getAvailableFilterOptions(): List<ListFilterOption> =
		favouritesRepository.getMostUpdatedCategories(
			limit = 4,
		).map {
			ListFilterOption.Favorite(it)
		}
}
