package org.koitharu.kotatsu.tv.core.exceptions

import org.koitharu.kotatsu.tv.parsers.model.Manga

class UnsupportedSourceException(
	message: String?,
	val manga: Manga?,
) : IllegalArgumentException(message)
