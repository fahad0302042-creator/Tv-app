package org.koitharu.kotatsu.tv.list.ui.adapter

import android.view.View
import org.koitharu.kotatsu.tv.core.ui.list.OnListItemClickListener
import org.koitharu.kotatsu.tv.list.ui.model.MangaListModel
import org.koitharu.kotatsu.tv.parsers.model.Manga
import org.koitharu.kotatsu.tv.parsers.model.MangaTag

interface MangaDetailsClickListener : OnListItemClickListener<MangaListModel> {

	fun onReadClick(manga: Manga, view: View)

	fun onTagClick(manga: Manga, tag: MangaTag, view: View)
}
