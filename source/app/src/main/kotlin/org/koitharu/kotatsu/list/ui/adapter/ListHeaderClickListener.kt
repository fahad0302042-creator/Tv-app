package org.koitharu.kotatsu.tv.list.ui.adapter

import android.view.View
import org.koitharu.kotatsu.tv.list.ui.model.ListHeader

interface ListHeaderClickListener {

	fun onListHeaderClick(item: ListHeader, view: View)
}
