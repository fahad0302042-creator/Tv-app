package org.koitharu.kotatsu.tv.alternatives.ui.covers

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import org.koitharu.kotatsu.tv.R
import org.koitharu.kotatsu.tv.core.model.getTitle
import org.koitharu.kotatsu.tv.core.ui.list.AdapterDelegateClickListenerAdapter
import org.koitharu.kotatsu.tv.core.ui.list.OnListItemClickListener
import org.koitharu.kotatsu.tv.databinding.ItemCoverAlternativeBinding
import org.koitharu.kotatsu.tv.list.ui.model.ListModel

fun alternativeCoverAD(
	listener: OnListItemClickListener<AlternativeCoverModel>,
) = adapterDelegateViewBinding<AlternativeCoverModel, ListModel, ItemCoverAlternativeBinding>(
	{ inflater, parent -> ItemCoverAlternativeBinding.inflate(inflater, parent, false) },
) {

	itemView.setOnClickListener(AdapterDelegateClickListenerAdapter(this, listener))

	bind {
		binding.textViewSource.text = if (item.isCurrent) {
			context.getString(R.string.current_cover)
		} else {
			item.source.getTitle(context)
		}
		binding.imageViewCover.setImageAsync(item.coverUrl, item.manga)
		// A ring on the thumbnail is what ties the carousel to the big preview above it.
		binding.imageViewCover.strokeWidth = if (item.isSelected) {
			context.resources.getDimension(R.dimen.cover_selection_stroke)
		} else {
			0f
		}
	}
}
