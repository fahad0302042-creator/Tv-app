package org.koitharu.kotatsu.tv.list.ui.model

import androidx.annotation.StringRes

data class LoadingState(
	@StringRes val textResId: Int = 0,
) : ListModel {

	override fun areItemsTheSame(other: ListModel): Boolean {
		return other is LoadingState
	}
}
