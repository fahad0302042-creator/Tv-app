package org.koitharu.kotatsu.tv.list.ui.model

import org.koitharu.kotatsu.tv.core.ui.model.MangaOverride
import org.koitharu.kotatsu.tv.parsers.model.Manga

data class MangaCompactListModel(
	override val manga: Manga,
	override val override: MangaOverride?,
	val subtitle: String,
	override val counter: Int,
) : MangaListModel()
