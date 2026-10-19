package org.koitharu.kotatsu.tv.bookmarks.ui.adapter

import android.content.Context
import org.koitharu.kotatsu.tv.bookmarks.domain.Bookmark
import org.koitharu.kotatsu.tv.core.ui.BaseListAdapter
import org.koitharu.kotatsu.tv.core.ui.list.OnListItemClickListener
import org.koitharu.kotatsu.tv.core.ui.list.fastscroll.FastScroller
import org.koitharu.kotatsu.tv.list.ui.adapter.ListHeaderClickListener
import org.koitharu.kotatsu.tv.list.ui.adapter.ListItemType
import org.koitharu.kotatsu.tv.list.ui.adapter.emptyStateListAD
import org.koitharu.kotatsu.tv.list.ui.adapter.errorStateListAD
import org.koitharu.kotatsu.tv.list.ui.adapter.listHeaderAD
import org.koitharu.kotatsu.tv.list.ui.adapter.loadingFooterAD
import org.koitharu.kotatsu.tv.list.ui.adapter.loadingStateAD
import org.koitharu.kotatsu.tv.list.ui.model.ListModel

class BookmarksAdapter(
	clickListener: OnListItemClickListener<Bookmark>,
	headerClickListener: ListHeaderClickListener?,
) : BaseListAdapter<ListModel>(), FastScroller.SectionIndexer {

	init {
		addDelegate(ListItemType.PAGE_THUMB, bookmarkLargeAD(clickListener))
		addDelegate(ListItemType.HEADER, listHeaderAD(headerClickListener))
		addDelegate(ListItemType.STATE_ERROR, errorStateListAD(null))
		addDelegate(ListItemType.FOOTER_LOADING, loadingFooterAD())
		addDelegate(ListItemType.STATE_LOADING, loadingStateAD())
		addDelegate(ListItemType.STATE_EMPTY, emptyStateListAD(null))
	}

	override fun getSectionText(context: Context, position: Int): CharSequence? {
		return findHeader(position)?.getText(context)
	}
}
