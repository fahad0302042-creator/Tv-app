package org.koitharu.kotatsu.tv.list.ui.adapter

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegate
import org.koitharu.kotatsu.tv.R
import org.koitharu.kotatsu.tv.list.ui.model.ListModel
import org.koitharu.kotatsu.tv.list.ui.model.LoadingFooter

fun loadingFooterAD() = adapterDelegate<LoadingFooter, ListModel>(R.layout.item_loading_footer) {
}