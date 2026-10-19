package org.koitharu.kotatsu.tv.list.ui.adapter

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import org.koitharu.kotatsu.tv.core.util.ext.setTextAndVisible
import org.koitharu.kotatsu.tv.databinding.ItemLoadingStateBinding
import org.koitharu.kotatsu.tv.list.ui.model.ListModel
import org.koitharu.kotatsu.tv.list.ui.model.LoadingState

fun loadingStateAD() = adapterDelegateViewBinding<LoadingState, ListModel, ItemLoadingStateBinding>(
	{ inflater, parent -> ItemLoadingStateBinding.inflate(inflater, parent, false) },
) {

	bind {
		binding.textViewMessage.setTextAndVisible(item.textResId)
	}
}
