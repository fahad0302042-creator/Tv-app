package org.koitharu.kotatsu.tv.bookmarks.ui.adapter

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import org.koitharu.kotatsu.tv.bookmarks.domain.Bookmark
import org.koitharu.kotatsu.tv.core.ui.list.AdapterDelegateClickListenerAdapter
import org.koitharu.kotatsu.tv.core.ui.list.OnListItemClickListener
import org.koitharu.kotatsu.tv.databinding.ItemBookmarkLargeBinding
import org.koitharu.kotatsu.tv.list.ui.model.ListModel

fun bookmarkLargeAD(
	clickListener: OnListItemClickListener<Bookmark>,
) = adapterDelegateViewBinding<Bookmark, ListModel, ItemBookmarkLargeBinding>(
	{ inflater, parent -> ItemBookmarkLargeBinding.inflate(inflater, parent, false) },
) {
	AdapterDelegateClickListenerAdapter(this, clickListener).attach(itemView)

	bind {
		binding.imageViewThumb.setImageAsync(item)
		binding.progressView.setProgress(item.percent, false)
	}
}
