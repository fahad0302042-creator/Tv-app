package org.koitharu.kotatsu.tv.core.exceptions

import org.koitharu.kotatsu.tv.details.ui.pager.EmptyMangaReason
import org.koitharu.kotatsu.tv.parsers.model.Manga

class EmptyMangaException(
    val reason: EmptyMangaReason?,
    val manga: Manga,
    cause: Throwable?
) : IllegalStateException(cause)
