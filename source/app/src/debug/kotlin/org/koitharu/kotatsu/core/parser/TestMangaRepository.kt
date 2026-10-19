package org.koitharu.kotatsu.tv.core.parser

import org.koitharu.kotatsu.tv.core.cache.MemoryContentCache
import org.koitharu.kotatsu.tv.core.model.TestMangaSource
import org.koitharu.kotatsu.tv.parsers.MangaLoaderContext
import org.koitharu.kotatsu.tv.parsers.model.Manga
import org.koitharu.kotatsu.tv.parsers.model.MangaChapter
import org.koitharu.kotatsu.tv.parsers.model.MangaListFilter
import org.koitharu.kotatsu.tv.parsers.model.MangaListFilterCapabilities
import org.koitharu.kotatsu.tv.parsers.model.MangaListFilterOptions
import org.koitharu.kotatsu.tv.parsers.model.MangaPage
import org.koitharu.kotatsu.tv.parsers.model.SortOrder
import java.util.EnumSet

/*
 This class is for parser development and testing purposes
 You can open it in the app via Settings -> Debug
 */
class TestMangaRepository(
	@Suppress("unused") private val loaderContext: MangaLoaderContext,
	cache: MemoryContentCache
) : CachingMangaRepository(cache) {

	override val source = TestMangaSource

	override val sortOrders: Set<SortOrder> = EnumSet.allOf(SortOrder::class.java)

	override var defaultSortOrder: SortOrder
		get() = sortOrders.first()
		set(value) = Unit

	override val filterCapabilities = MangaListFilterCapabilities()

	override suspend fun getFilterOptions() = MangaListFilterOptions()

	override suspend fun getList(
		offset: Int,
		order: SortOrder?,
		filter: MangaListFilter?
	): List<Manga> = TODO("Get manga list by filter")

	override suspend fun getDetailsImpl(
		manga: Manga
	): Manga = TODO("Fetch manga details")

	override suspend fun getPagesImpl(
		chapter: MangaChapter
	): List<MangaPage> = TODO("Get pages for specific chapter")

	override suspend fun getPageUrl(
		page: MangaPage
	): String = TODO("Return direct url of page image or page.url if it is already a direct url")

	override suspend fun getRelatedMangaImpl(
		seed: Manga
	): List<Manga> = TODO("Get list of related manga. This method is optional and parser library has a default implementation")
}
