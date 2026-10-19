package org.koitharu.kotatsu.tv.scrobbling.common.domain

import org.koitharu.kotatsu.tv.scrobbling.anilist.data.AniListRepository
import org.koitharu.kotatsu.tv.scrobbling.common.data.ScrobblerRepository
import org.koitharu.kotatsu.tv.scrobbling.common.domain.model.ScrobblerService
import org.koitharu.kotatsu.tv.scrobbling.kitsu.data.KitsuRepository
import org.koitharu.kotatsu.tv.scrobbling.mal.data.MALRepository
import org.koitharu.kotatsu.tv.scrobbling.shikimori.data.ShikimoriRepository
import javax.inject.Inject
import javax.inject.Provider

class ScrobblerRepositoryMap @Inject constructor(
	private val shikimoriRepository: Provider<ShikimoriRepository>,
	private val aniListRepository: Provider<AniListRepository>,
	private val malRepository: Provider<MALRepository>,
	private val kitsuRepository: Provider<KitsuRepository>,
) {

	operator fun get(scrobblerService: ScrobblerService): ScrobblerRepository = when (scrobblerService) {
		ScrobblerService.SHIKIMORI -> shikimoriRepository
		ScrobblerService.ANILIST -> aniListRepository
		ScrobblerService.MAL -> malRepository
		ScrobblerService.KITSU -> kitsuRepository
	}.get()
}
