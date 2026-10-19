package org.koitharu.kotatsu.tv.list.ui.adapter

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import org.koitharu.kotatsu.tv.databinding.ItemButtonFooterBinding
import org.koitharu.kotatsu.tv.list.ui.model.ButtonFooter
import org.koitharu.kotatsu.tv.list.ui.model.ListModel

fun buttonFooterAD(
	listener: ListStateHolderListener,
) = adapterDelegateViewBinding<ButtonFooter, ListModel, ItemButtonFooterBinding>(
	{ inflater, parent -> ItemButtonFooterBinding.inflate(inflater, parent, false) },
) {

	binding.button.setOnClickListener {
		listener.onFooterButtonClick()
	}

	bind {
		binding.button.setText(item.textResId)
	}
}
