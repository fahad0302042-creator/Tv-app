package org.koitharu.kotatsu.tv.core.parser

import org.koitharu.kotatsu.tv.core.cache.MemoryContentCache
import org.koitharu.kotatsu.tv.core.model.TestMangaSource
import org.koitharu.kotatsu.tv.parsers.MangaLoaderContext

@Suppress("unused")
class TestMangaRepository(
	private val loaderContext: MangaLoaderContext,
	cache: MemoryContentCache
) : EmptyMangaRepository(TestMangaSource)
