package org.koitharu.kotatsu.tv.search.ui.suggestion.adapter

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import org.koitharu.kotatsu.tv.core.ui.widgets.ChipsView
import org.koitharu.kotatsu.tv.databinding.ItemSearchSuggestionTagsBinding
import org.koitharu.kotatsu.tv.parsers.model.MangaTag
import org.koitharu.kotatsu.tv.search.ui.suggestion.SearchSuggestionListener
import org.koitharu.kotatsu.tv.search.ui.suggestion.model.SearchSuggestionItem

fun searchSuggestionTagsAD(
	listener: SearchSuggestionListener,
) = adapterDelegateViewBinding<SearchSuggestionItem.Tags, SearchSuggestionItem, ItemSearchSuggestionTagsBinding>(
	{ layoutInflater, parent -> ItemSearchSuggestionTagsBinding.inflate(layoutInflater, parent, false) },
) {

	binding.chipsGenres.onChipClickListener = ChipsView.OnChipClickListener { _, data ->
		listener.onTagClick(data as? MangaTag ?: return@OnChipClickListener)
	}

	bind {
		binding.chipsGenres.setChips(item.tags)
	}
}
