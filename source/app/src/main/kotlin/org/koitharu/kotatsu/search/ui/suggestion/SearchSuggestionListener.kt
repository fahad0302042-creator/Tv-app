package org.koitharu.kotatsu.tv.search.ui.suggestion

import android.text.TextWatcher
import android.widget.TextView
import org.koitharu.kotatsu.tv.core.model.FavouriteCategory
import org.koitharu.kotatsu.tv.parsers.model.Manga
import org.koitharu.kotatsu.tv.parsers.model.MangaSource
import org.koitharu.kotatsu.tv.parsers.model.MangaTag
import org.koitharu.kotatsu.tv.search.domain.SearchKind

interface SearchSuggestionListener : TextWatcher, TextView.OnEditorActionListener {

	fun onMangaClick(manga: Manga)

	fun onQueryClick(query: String, kind: SearchKind, submit: Boolean)

	fun onSourceToggle(source: MangaSource, isEnabled: Boolean)

	fun onSourceClick(source: MangaSource)

	fun onTagClick(tag: MangaTag)

	fun onScopeChanged(isScoped: Boolean)

	fun onFavouriteCategoryClick(category: FavouriteCategory)

	/** Expands the single "favourites" entry into every list. */
	fun onFavouritesGroupClick()
}
