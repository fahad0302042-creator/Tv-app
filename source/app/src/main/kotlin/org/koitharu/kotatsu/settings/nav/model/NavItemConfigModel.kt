package org.koitharu.kotatsu.tv.settings.nav.model

import androidx.annotation.StringRes
import org.koitharu.kotatsu.tv.core.prefs.NavItem
import org.koitharu.kotatsu.tv.list.ui.model.ListModel

data class NavItemConfigModel(
	val item: NavItem,
	@StringRes val disabledHintResId: Int,
) : ListModel {

	override fun areItemsTheSame(other: ListModel): Boolean {
		return other is NavItemConfigModel && other.item == item
	}
}
