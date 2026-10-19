package org.koitharu.kotatsu.tv.settings.sources.catalog

import org.koitharu.kotatsu.tv.parsers.model.ContentType

data class SourcesCatalogFilter(
	val types: Set<ContentType>,
	val locale: String?,
	val isNewOnly: Boolean,
)
