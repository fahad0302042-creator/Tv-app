package org.koitharu.kotatsu.tv.core.model

import org.koitharu.kotatsu.tv.parsers.model.MangaSource

data class MangaSourceInfo(
	val mangaSource: MangaSource,
	val isEnabled: Boolean,
	val isPinned: Boolean,
) : MangaSource by mangaSource
