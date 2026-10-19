package org.koitharu.kotatsu.tv.list.ui.adapter

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import org.koitharu.kotatsu.tv.core.ui.widgets.TipView
import org.koitharu.kotatsu.tv.databinding.ItemTip2Binding
import org.koitharu.kotatsu.tv.list.ui.model.ListModel
import org.koitharu.kotatsu.tv.list.ui.model.TipModel

fun tipAD(
	listener: TipView.OnButtonClickListener,
) = adapterDelegateViewBinding<TipModel, ListModel, ItemTip2Binding>(
	{ layoutInflater, parent -> ItemTip2Binding.inflate(layoutInflater, parent, false) }
) {

	binding.root.onButtonClickListener = listener

	bind {
		with(binding.root) {
			tag = item
			setTitle(item.title)
			setText(item.text)
			setIcon(item.icon)
			setPrimaryButtonText(item.primaryButtonText)
			setSecondaryButtonText(item.secondaryButtonText)
		}
	}
}
