package org.koitharu.kotatsu.tv.settings.tracker.categories

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import org.koitharu.kotatsu.tv.core.model.FavouriteCategory
import org.koitharu.kotatsu.tv.core.ui.list.AdapterDelegateClickListenerAdapter
import org.koitharu.kotatsu.tv.core.ui.list.OnListItemClickListener
import org.koitharu.kotatsu.tv.databinding.ItemCategoryCheckableMultipleBinding

fun trackerCategoryAD(
	listener: OnListItemClickListener<FavouriteCategory>,
) = adapterDelegateViewBinding<FavouriteCategory, FavouriteCategory, ItemCategoryCheckableMultipleBinding>(
	{ layoutInflater, parent -> ItemCategoryCheckableMultipleBinding.inflate(layoutInflater, parent, false) },
) {
	val eventListener = AdapterDelegateClickListenerAdapter(this, listener)
	itemView.setOnClickListener(eventListener)

	bind {
		binding.root.text = item.title
		binding.root.isChecked = item.isTrackingEnabled
	}
}
