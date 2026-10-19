package org.koitharu.kotatsu.tv.favourites.ui.categories.select.adapter

import org.koitharu.kotatsu.tv.core.ui.BaseListAdapter
import org.koitharu.kotatsu.tv.core.ui.list.OnListItemClickListener
import org.koitharu.kotatsu.tv.favourites.ui.categories.select.model.MangaCategoryItem
import org.koitharu.kotatsu.tv.list.ui.adapter.ListItemType
import org.koitharu.kotatsu.tv.list.ui.adapter.emptyStateListAD
import org.koitharu.kotatsu.tv.list.ui.adapter.loadingStateAD
import org.koitharu.kotatsu.tv.list.ui.model.ListModel

class MangaCategoriesAdapter(
	clickListener: OnListItemClickListener<MangaCategoryItem>,
) : BaseListAdapter<ListModel>() {

	init {
		addDelegate(ListItemType.NAV_ITEM, mangaCategoryAD(clickListener))
		addDelegate(ListItemType.STATE_LOADING, loadingStateAD())
		addDelegate(ListItemType.STATE_EMPTY, emptyStateListAD(null))
	}
}
