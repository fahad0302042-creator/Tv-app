package org.koitharu.kotatsu.tv.core.parser

import android.content.Context
import androidx.annotation.AnyThread
import androidx.collection.ArrayMap
import dagger.hilt.android.qualifiers.ApplicationContext
import org.koitharu.kotatsu.tv.core.cache.MemoryContentCache
import org.koitharu.kotatsu.tv.core.model.LocalMangaSource
import org.koitharu.kotatsu.tv.core.model.MangaSourceInfo
import org.koitharu.kotatsu.tv.core.model.TestMangaSource
import org.koitharu.kotatsu.tv.core.model.UnknownMangaSource
import org.koitharu.kotatsu.tv.core.parser.external.ExternalMangaRepository
import org.koitharu.kotatsu.tv.core.parser.external.ExternalMangaSource
import org.koitharu.kotatsu.tv.local.data.LocalMangaRepository
import org.koitharu.kotatsu.tv.parsers.MangaLoaderContext
import org.koitharu.kotatsu.tv.parsers.model.Manga
import org.koitharu.kotatsu.tv.parsers.model.MangaChapter
import org.koitharu.kotatsu.tv.parsers.model.MangaListFilter
import org.koitharu.kotatsu.tv.parsers.model.MangaListFilterCapabilities
import org.koitharu.kotatsu.tv.parsers.model.MangaListFilterOptions
import org.koitharu.kotatsu.tv.parsers.model.MangaPage
import org.koitharu.kotatsu.tv.parsers.model.MangaParserSource
import org.koitharu.kotatsu.tv.parsers.model.MangaSource
import org.koitharu.kotatsu.tv.parsers.model.SortOrder
import java.lang.ref.WeakReference
import javax.inject.Inject
import javax.inject.Singleton

interface MangaRepository {

	val source: MangaSource

	val sortOrders: Set<SortOrder>

	var defaultSortOrder: SortOrder

	val filterCapabilities: MangaListFilterCapabilities

	suspend fun getList(offset: Int, order: SortOrder?, filter: MangaListFilter?): List<Manga>

	suspend fun getDetails(manga: Manga): Manga

	suspend fun getPages(chapter: MangaChapter): List<MangaPage>

	suspend fun getPageUrl(page: MangaPage): String

	suspend fun getFilterOptions(): MangaListFilterOptions

	suspend fun getRelated(seed: Manga): List<Manga>

	suspend fun find(manga: Manga): Manga? {
		val list = getList(0, SortOrder.RELEVANCE, MangaListFilter(query = manga.title))
		return list.find { x -> x.id == manga.id }
	}

	@Singleton
	class Factory @Inject constructor(
		@ApplicationContext private val context: Context,
		private val localMangaRepository: LocalMangaRepository,
		private val loaderContext: MangaLoaderContext,
		private val contentCache: MemoryContentCache,
		private val mirrorSwitcher: MirrorSwitcher,
	) {

		private val cache = ArrayMap<MangaSource, WeakReference<MangaRepository>>()

		@AnyThread
		fun create(source: MangaSource): MangaRepository {
			when (source) {
				is MangaSourceInfo -> return create(source.mangaSource)
				LocalMangaSource -> return localMangaRepository
				UnknownMangaSource -> return EmptyMangaRepository(source)
			}
			cache[source]?.get()?.let { return it }
			return synchronized(cache) {
				cache[source]?.get()?.let { return it }
				val repository = createRepository(source)
				if (repository != null) {
					cache[source] = WeakReference(repository)
					repository
				} else {
					EmptyMangaRepository(source)
				}
			}
		}

		private fun createRepository(source: MangaSource): MangaRepository? = when (source) {
			is MangaParserSource -> ParserMangaRepository(
				parser = loaderContext.newParserInstance(source),
				cache = contentCache,
				mirrorSwitcher = mirrorSwitcher,
			)

			TestMangaSource -> TestMangaRepository(
				loaderContext = loaderContext,
				cache = contentCache,
			)

			is ExternalMangaSource -> if (source.isAvailable(context)) {
				ExternalMangaRepository(
					contentResolver = context.contentResolver,
					source = source,
					cache = contentCache,
				)
			} else {
				EmptyMangaRepository(source)
			}

			else -> null
		}
	}
}
