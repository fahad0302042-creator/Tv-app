package org.koitharu.kotatsu.tv.history.ui

import android.content.Context
import org.koitharu.kotatsu.tv.core.ui.list.fastscroll.FastScroller
import org.koitharu.kotatsu.tv.list.ui.adapter.MangaListAdapter
import org.koitharu.kotatsu.tv.list.ui.adapter.MangaListListener
import org.koitharu.kotatsu.tv.list.ui.size.ItemSizeResolver

class HistoryListAdapter(
	listener: MangaListListener,
	sizeResolver: ItemSizeResolver,
) : MangaListAdapter(listener, sizeResolver), FastScroller.SectionIndexer {

	override fun getSectionText(context: Context, position: Int): CharSequence? {
		return findHeader(position)?.getText(context)
	}
}
