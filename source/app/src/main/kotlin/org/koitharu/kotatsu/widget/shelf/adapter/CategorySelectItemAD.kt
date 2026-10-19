package org.koitharu.kotatsu.tv.widget.shelf.adapter

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import org.koitharu.kotatsu.tv.R
import org.koitharu.kotatsu.tv.core.ui.list.OnListItemClickListener
import org.koitharu.kotatsu.tv.databinding.ItemCategoryCheckableSingleBinding
import org.koitharu.kotatsu.tv.widget.shelf.model.CategoryItem

fun categorySelectItemAD(
	clickListener: OnListItemClickListener<CategoryItem>
) = adapterDelegateViewBinding<CategoryItem, CategoryItem, ItemCategoryCheckableSingleBinding>(
	{ inflater, parent -> ItemCategoryCheckableSingleBinding.inflate(inflater, parent, false) },
) {

	itemView.setOnClickListener {
		clickListener.onItemClick(item, it)
	}

	bind {
		with(binding.checkedTextView) {
			text = item.name ?: getString(R.string.all_favourites)
			isChecked = item.isSelected
		}
	}
}
