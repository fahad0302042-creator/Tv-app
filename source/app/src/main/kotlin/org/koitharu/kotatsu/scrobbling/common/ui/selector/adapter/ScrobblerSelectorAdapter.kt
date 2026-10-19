package org.koitharu.kotatsu.tv.scrobbling.common.ui.selector.adapter

import org.koitharu.kotatsu.tv.core.ui.BaseListAdapter
import org.koitharu.kotatsu.tv.core.ui.list.OnListItemClickListener
import org.koitharu.kotatsu.tv.list.ui.adapter.ListItemType
import org.koitharu.kotatsu.tv.list.ui.adapter.ListStateHolderListener
import org.koitharu.kotatsu.tv.list.ui.adapter.loadingFooterAD
import org.koitharu.kotatsu.tv.list.ui.adapter.loadingStateAD
import org.koitharu.kotatsu.tv.list.ui.model.ListModel
import org.koitharu.kotatsu.tv.scrobbling.common.domain.model.ScrobblerManga

class ScrobblerSelectorAdapter(
	clickListener: OnListItemClickListener<ScrobblerManga>,
	stateHolderListener: ListStateHolderListener,
) : BaseListAdapter<ListModel>() {

	init {
		addDelegate(ListItemType.STATE_LOADING, loadingStateAD())
		addDelegate(ListItemType.MANGA_SCROBBLING, scrobblingMangaAD(clickListener))
		addDelegate(ListItemType.FOOTER_LOADING, loadingFooterAD())
		addDelegate(ListItemType.HINT_EMPTY, scrobblerHintAD(stateHolderListener))
	}
}
