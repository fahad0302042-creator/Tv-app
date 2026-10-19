package org.koitharu.kotatsu.tv.settings.tracker.categories

import org.koitharu.kotatsu.tv.core.model.FavouriteCategory
import org.koitharu.kotatsu.tv.core.ui.BaseListAdapter
import org.koitharu.kotatsu.tv.core.ui.list.OnListItemClickListener

class TrackerCategoriesConfigAdapter(
	listener: OnListItemClickListener<FavouriteCategory>,
) : BaseListAdapter<FavouriteCategory>() {

	init {
		delegatesManager.addDelegate(trackerCategoryAD(listener))
	}
}
