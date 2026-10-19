package org.koitharu.kotatsu.tv.details.ui.scrobbling

import org.koitharu.kotatsu.tv.core.nav.AppRouter
import org.koitharu.kotatsu.tv.core.ui.BaseListAdapter
import org.koitharu.kotatsu.tv.list.ui.model.ListModel

class ScrollingInfoAdapter(
	router: AppRouter,
) : BaseListAdapter<ListModel>() {

	init {
		delegatesManager.addDelegate(scrobblingInfoAD(router))
	}
}
