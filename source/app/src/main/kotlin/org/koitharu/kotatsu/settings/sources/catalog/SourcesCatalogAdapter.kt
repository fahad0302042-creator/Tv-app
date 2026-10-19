package org.koitharu.kotatsu.tv.settings.sources.catalog

import android.content.Context
import org.koitharu.kotatsu.tv.core.model.getTitle
import org.koitharu.kotatsu.tv.core.ui.BaseListAdapter
import org.koitharu.kotatsu.tv.core.ui.list.OnListItemClickListener
import org.koitharu.kotatsu.tv.core.ui.list.fastscroll.FastScroller
import org.koitharu.kotatsu.tv.list.ui.adapter.ListItemType
import org.koitharu.kotatsu.tv.list.ui.adapter.loadingStateAD
import org.koitharu.kotatsu.tv.list.ui.model.ListModel

class SourcesCatalogAdapter(
	listener: OnListItemClickListener<SourceCatalogItem.Source>,
) : BaseListAdapter<ListModel>(), FastScroller.SectionIndexer {

	init {
		addDelegate(ListItemType.CHAPTER_LIST, sourceCatalogItemSourceAD(listener))
		addDelegate(ListItemType.HINT_EMPTY, sourceCatalogItemHintAD())
		addDelegate(ListItemType.STATE_LOADING, loadingStateAD())
	}

	override fun getSectionText(context: Context, position: Int): CharSequence? {
		return (items.getOrNull(position) as? SourceCatalogItem.Source)?.source?.getTitle(context)?.take(1)
	}
}
