package org.koitharu.kotatsu.tv.scrobbling.common.ui.config.adapter

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import org.koitharu.kotatsu.tv.core.ui.list.AdapterDelegateClickListenerAdapter
import org.koitharu.kotatsu.tv.core.ui.list.OnListItemClickListener
import org.koitharu.kotatsu.tv.databinding.ItemScrobblingMangaBinding
import org.koitharu.kotatsu.tv.list.ui.model.ListModel
import org.koitharu.kotatsu.tv.scrobbling.common.domain.model.ScrobblingInfo

fun scrobblingMangaAD(
	clickListener: OnListItemClickListener<ScrobblingInfo>,
) = adapterDelegateViewBinding<ScrobblingInfo, ListModel, ItemScrobblingMangaBinding>(
	{ layoutInflater, parent -> ItemScrobblingMangaBinding.inflate(layoutInflater, parent, false) },
) {

	AdapterDelegateClickListenerAdapter(this, clickListener).attach(itemView)

	bind {
		binding.imageViewCover.setImageAsync(item.coverUrl, null)
		binding.textViewTitle.text = item.title
		binding.ratingBar.rating = item.rating * binding.ratingBar.numStars
	}
}
