package org.koitharu.kotatsu.tv.reader.ui

import org.koitharu.kotatsu.tv.bookmarks.domain.Bookmark
import org.koitharu.kotatsu.tv.parsers.model.MangaChapter
import org.koitharu.kotatsu.tv.reader.ui.pager.ReaderPage

interface ReaderNavigationCallback {

	fun onPageSelected(page: ReaderPage): Boolean

	fun onChapterSelected(chapter: MangaChapter): Boolean

	fun onBookmarkSelected(bookmark: Bookmark): Boolean = onPageSelected(
		ReaderPage(bookmark.toMangaPage(), bookmark.page, bookmark.chapterId),
	)
}
