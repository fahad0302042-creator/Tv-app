package org.koitharu.kotatsu.tv.list.ui.adapter

import org.koitharu.kotatsu.tv.list.domain.ListFilterOption

interface QuickFilterClickListener {

	fun onFilterOptionClick(option: ListFilterOption)
}
