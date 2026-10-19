package org.koitharu.kotatsu.tv.reader.ui

import org.koitharu.kotatsu.tv.reader.ui.pager.ReaderPage

data class ReaderContent(
	val pages: List<ReaderPage>,
	val state: ReaderState?
)