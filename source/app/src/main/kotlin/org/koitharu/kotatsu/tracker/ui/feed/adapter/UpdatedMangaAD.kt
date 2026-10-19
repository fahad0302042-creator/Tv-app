package org.koitharu.kotatsu.tv.tracker.ui.feed.adapter

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import org.koitharu.kotatsu.tv.R
import org.koitharu.kotatsu.tv.core.ui.BaseListAdapter
import org.koitharu.kotatsu.tv.core.ui.list.OnListItemClickListener
import org.koitharu.kotatsu.tv.databinding.ItemListGroupBinding
import org.koitharu.kotatsu.tv.list.ui.adapter.ListHeaderClickListener
import org.koitharu.kotatsu.tv.list.ui.adapter.ListItemType
import org.koitharu.kotatsu.tv.list.ui.adapter.mangaGridItemAD
import org.koitharu.kotatsu.tv.list.ui.model.ListHeader
import org.koitharu.kotatsu.tv.list.ui.model.ListModel
import org.koitharu.kotatsu.tv.list.ui.model.MangaListModel
import org.koitharu.kotatsu.tv.list.ui.size.ItemSizeResolver
import org.koitharu.kotatsu.tv.tracker.ui.feed.model.UpdatedMangaHeader

fun updatedMangaAD(
	sizeResolver: ItemSizeResolver,
	listener: OnListItemClickListener<MangaListModel>,
	headerClickListener: ListHeaderClickListener,
) = adapterDelegateViewBinding<UpdatedMangaHeader, ListModel, ItemListGroupBinding>(
	{ layoutInflater, parent -> ItemListGroupBinding.inflate(layoutInflater, parent, false) },
) {

	val adapter = BaseListAdapter<ListModel>()
		.addDelegate(ListItemType.MANGA_GRID, mangaGridItemAD(sizeResolver, listener))
	binding.recyclerView.adapter = adapter
	binding.buttonMore.setOnClickListener { v ->
		headerClickListener.onListHeaderClick(ListHeader(0, payload = item), v)
	}
	binding.textViewTitle.setText(R.string.updates)
	binding.buttonMore.setText(R.string.more)

	bind {
		adapter.items = item.list
	}
}
