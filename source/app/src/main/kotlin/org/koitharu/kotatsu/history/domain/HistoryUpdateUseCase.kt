package org.koitharu.kotatsu.tv.history.domain

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koitharu.kotatsu.tv.core.util.ext.printStackTraceDebug
import org.koitharu.kotatsu.tv.core.util.ext.processLifecycleScope
import org.koitharu.kotatsu.tv.history.data.HistoryRepository
import org.koitharu.kotatsu.tv.parsers.model.Manga
import org.koitharu.kotatsu.tv.parsers.util.runCatchingCancellable
import org.koitharu.kotatsu.tv.reader.ui.ReaderState
import javax.inject.Inject

class HistoryUpdateUseCase @Inject constructor(
	private val historyRepository: HistoryRepository,
) {

	suspend operator fun invoke(manga: Manga, readerState: ReaderState, percent: Float) {
		historyRepository.addOrUpdate(
			manga = manga,
			chapterId = readerState.chapterId,
			page = readerState.page,
			scroll = readerState.scroll,
			percent = percent,
			force = false,
		)
	}

	fun invokeAsync(
		manga: Manga,
		readerState: ReaderState,
		percent: Float
	) = processLifecycleScope.launch(Dispatchers.Default, CoroutineStart.ATOMIC) {
		runCatchingCancellable {
			withContext(NonCancellable) {
				invoke(manga, readerState, percent)
			}
		}.onFailure {
			it.printStackTraceDebug()
		}
	}
}
