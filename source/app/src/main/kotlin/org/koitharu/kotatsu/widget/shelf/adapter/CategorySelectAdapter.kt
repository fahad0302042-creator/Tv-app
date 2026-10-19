package org.koitharu.kotatsu.tv.widget.shelf.adapter

import org.koitharu.kotatsu.tv.core.ui.BaseListAdapter
import org.koitharu.kotatsu.tv.core.ui.list.OnListItemClickListener
import org.koitharu.kotatsu.tv.widget.shelf.model.CategoryItem

class CategorySelectAdapter(
	clickListener: OnListItemClickListener<CategoryItem>
) : BaseListAdapter<CategoryItem>() {

	init {
		delegatesManager.addDelegate(categorySelectItemAD(clickListener))
	}
}
