package org.koitharu.kotatsu.tv.settings.sources.adapter

import org.koitharu.kotatsu.tv.core.ui.ReorderableListAdapter
import org.koitharu.kotatsu.tv.settings.sources.model.SourceConfigItem

class SourceConfigAdapter(
	listener: SourceConfigListener,
) : ReorderableListAdapter<SourceConfigItem>() {

	init {
		with(delegatesManager) {
			addDelegate(sourceConfigItemDelegate2(listener))
			addDelegate(sourceConfigEmptySearchDelegate())
			addDelegate(sourceConfigTipDelegate(listener))
		}
	}
}
