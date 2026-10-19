package org.koitharu.kotatsu.tv.explore.ui.adapter

import org.koitharu.kotatsu.tv.core.ui.BaseListAdapter
import org.koitharu.kotatsu.tv.core.ui.list.OnListItemClickListener
import org.koitharu.kotatsu.tv.explore.ui.model.MangaSourceItem
import org.koitharu.kotatsu.tv.list.ui.adapter.ListItemType
import org.koitharu.kotatsu.tv.list.ui.adapter.emptyHintAD
import org.koitharu.kotatsu.tv.list.ui.adapter.listHeaderAD
import org.koitharu.kotatsu.tv.list.ui.adapter.loadingStateAD
import org.koitharu.kotatsu.tv.list.ui.model.ListModel
import org.koitharu.kotatsu.tv.parsers.model.Manga

class ExploreAdapter(
	listener: ExploreListEventListener,
	clickListener: OnListItemClickListener<MangaSourceItem>,
	mangaClickListener: OnListItemClickListener<Manga>,
) : BaseListAdapter<ListModel>() {

	init {
		addDelegate(ListItemType.EXPLORE_BUTTONS, exploreButtonsAD(listener))
		addDelegate(
			ListItemType.EXPLORE_SUGGESTION,
			exploreRecommendationItemAD(mangaClickListener),
		)
		addDelegate(ListItemType.HEADER, listHeaderAD(listener))
		addDelegate(ListItemType.EXPLORE_SOURCE_LIST, exploreSourceListItemAD(clickListener))
		addDelegate(ListItemType.EXPLORE_SOURCE_GRID, exploreSourceGridItemAD(clickListener))
		addDelegate(ListItemType.HINT_EMPTY, emptyHintAD(listener))
		addDelegate(ListItemType.STATE_LOADING, loadingStateAD())
	}
}
