package org.koitharu.kotatsu.tv.core.exceptions

import okio.IOException
import org.koitharu.kotatsu.tv.parsers.model.MangaSource

class InteractiveActionRequiredException(
	val source: MangaSource,
	val url: String,
) : IOException("Interactive action is required for ${source.name}")
