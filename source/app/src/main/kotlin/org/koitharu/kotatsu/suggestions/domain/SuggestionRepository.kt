package org.koitharu.kotatsu.tv.suggestions.domain

import androidx.room.withTransaction
import kotlinx.coroutines.flow.Flow
import org.koitharu.kotatsu.tv.core.db.MangaDatabase
import org.koitharu.kotatsu.tv.core.db.entity.toEntities
import org.koitharu.kotatsu.tv.core.db.entity.toEntity
import org.koitharu.kotatsu.tv.core.db.entity.toManga
import org.koitharu.kotatsu.tv.core.db.entity.toMangaTagsList
import org.koitharu.kotatsu.tv.core.model.toMangaSources
import org.koitharu.kotatsu.tv.core.util.ext.mapItems
import org.koitharu.kotatsu.tv.list.domain.ListFilterOption
import org.koitharu.kotatsu.tv.parsers.model.Manga
import org.koitharu.kotatsu.tv.parsers.model.MangaSource
import org.koitharu.kotatsu.tv.parsers.model.MangaTag
import org.koitharu.kotatsu.tv.suggestions.data.SuggestionEntity
import org.koitharu.kotatsu.tv.suggestions.data.SuggestionWithManga
import javax.inject.Inject

class SuggestionRepository @Inject constructor(
	private val db: MangaDatabase,
) {

	fun observeAll(): Flow<List<Manga>> {
		return db.getSuggestionDao().observeAll().mapItems {
			it.toManga()
		}
	}

	fun observeAll(limit: Int, filterOptions: Set<ListFilterOption>): Flow<List<Manga>> {
		return db.getSuggestionDao().observeAll(limit, filterOptions).mapItems {
			it.toManga()
		}
	}

	/** The stored suggestions, best first, with their tags. */
	suspend fun getTopList(limit: Int): List<Manga> {
		return db.getSuggestionDao().getTopManga(limit).map {
			it.toManga()
		}
	}

	suspend fun getRandomList(limit: Int): List<Manga> {
		return db.getSuggestionDao().getRandom(limit).map {
			it.toManga()
		}
	}

	suspend fun clear() {
		db.getSuggestionDao().deleteAll()
	}

	suspend fun isEmpty(): Boolean {
		return db.getSuggestionDao().count() == 0
	}

	suspend fun getTopTags(limit: Int): List<MangaTag> {
		return db.getSuggestionDao().getTopTags(limit)
			.toMangaTagsList()
	}

	suspend fun getTopSources(limit: Int): List<MangaSource> {
		return db.getSuggestionDao().getTopSources(limit)
			.toMangaSources()
	}

	suspend fun replace(suggestions: Iterable<MangaSuggestion>) {
		db.withTransaction {
			db.getSuggestionDao().deleteAll()
			suggestions.forEach { (manga, relevance) ->
				val tags = manga.tags.toEntities()
				db.getTagsDao().upsert(tags)
				db.getMangaDao().upsert(manga.toEntity(), tags)
				db.getSuggestionDao().upsert(
					SuggestionEntity(
						mangaId = manga.id,
						relevance = relevance,
						createdAt = System.currentTimeMillis(),
					),
				)
			}
		}
	}

	private fun SuggestionWithManga.toManga() = manga.toManga(emptySet(), null)
}
