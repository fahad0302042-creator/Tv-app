package org.koitharu.kotatsu.tv.scrobbling.common.ui.config.adapter

import org.koitharu.kotatsu.tv.core.ui.BaseListAdapter
import org.koitharu.kotatsu.tv.core.ui.list.OnListItemClickListener
import org.koitharu.kotatsu.tv.list.ui.adapter.ListItemType
import org.koitharu.kotatsu.tv.list.ui.adapter.emptyStateListAD
import org.koitharu.kotatsu.tv.list.ui.model.ListModel
import org.koitharu.kotatsu.tv.scrobbling.common.domain.model.ScrobblingInfo

class ScrobblingMangaAdapter(
	clickListener: OnListItemClickListener<ScrobblingInfo>,
) : BaseListAdapter<ListModel>() {

	init {
		addDelegate(ListItemType.HEADER, scrobblingHeaderAD())
		addDelegate(ListItemType.STATE_EMPTY, emptyStateListAD(null))
		addDelegate(ListItemType.MANGA_SCROBBLING, scrobblingMangaAD(clickListener))
	}
}
