package org.koitharu.kotatsu.tv.favourites.ui.categories.adapter

import org.koitharu.kotatsu.tv.core.ui.ReorderableListAdapter
import org.koitharu.kotatsu.tv.favourites.ui.categories.FavouriteCategoriesListListener
import org.koitharu.kotatsu.tv.list.ui.adapter.ListItemType
import org.koitharu.kotatsu.tv.list.ui.adapter.ListStateHolderListener
import org.koitharu.kotatsu.tv.list.ui.adapter.emptyStateListAD
import org.koitharu.kotatsu.tv.list.ui.adapter.loadingStateAD
import org.koitharu.kotatsu.tv.list.ui.model.ListModel

class CategoriesAdapter(
	onItemClickListener: FavouriteCategoriesListListener,
	listListener: ListStateHolderListener,
) : ReorderableListAdapter<ListModel>() {

	init {
		addDelegate(ListItemType.CATEGORY_LARGE, categoryAD(onItemClickListener))
		addDelegate(ListItemType.NAV_ITEM, allCategoriesAD(onItemClickListener))
		addDelegate(ListItemType.STATE_EMPTY, emptyStateListAD(listListener))
		addDelegate(ListItemType.STATE_LOADING, loadingStateAD())
	}
}
