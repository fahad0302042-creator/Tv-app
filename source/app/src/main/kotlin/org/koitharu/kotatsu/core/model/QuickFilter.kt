package org.koitharu.kotatsu.tv.core.model

import org.koitharu.kotatsu.tv.core.ui.widgets.ChipsView
import org.koitharu.kotatsu.tv.list.domain.ListFilterOption

fun ListFilterOption.toChipModel(isChecked: Boolean) = ChipsView.ChipModel(
	title = titleText,
	titleResId = titleResId,
	icon = iconResId,
	iconData = getIconData(),
	isChecked = isChecked,
	counter = if (this is ListFilterOption.Branch) chaptersCount else 0,
	data = this,
)
