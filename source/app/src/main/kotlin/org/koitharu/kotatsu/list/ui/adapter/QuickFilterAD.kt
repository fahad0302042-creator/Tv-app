package org.koitharu.kotatsu.tv.list.ui.adapter

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import org.koitharu.kotatsu.tv.core.ui.widgets.ChipsView
import org.koitharu.kotatsu.tv.databinding.ItemQuickFilterBinding
import org.koitharu.kotatsu.tv.list.domain.ListFilterOption
import org.koitharu.kotatsu.tv.list.ui.model.ListModel
import org.koitharu.kotatsu.tv.list.ui.model.QuickFilter

fun quickFilterAD(
	listener: QuickFilterClickListener,
) = adapterDelegateViewBinding<QuickFilter, ListModel, ItemQuickFilterBinding>(
	{ layoutInflater, parent -> ItemQuickFilterBinding.inflate(layoutInflater, parent, false) }
) {

	binding.chipsTags.onChipClickListener = ChipsView.OnChipClickListener { chip, data ->
		if (data is ListFilterOption) {
			listener.onFilterOptionClick(data)
		}
	}

	bind {
		binding.chipsTags.setChips(item.items)
	}
}
