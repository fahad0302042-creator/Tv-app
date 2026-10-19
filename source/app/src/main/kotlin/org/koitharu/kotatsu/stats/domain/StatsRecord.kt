package org.koitharu.kotatsu.tv.stats.domain

import org.koitharu.kotatsu.tv.details.data.ReadingTime
import org.koitharu.kotatsu.tv.list.ui.model.ListModel
import org.koitharu.kotatsu.tv.parsers.model.Manga
import java.util.concurrent.TimeUnit

data class StatsRecord(
	val manga: Manga?,
	val duration: Long,
) : ListModel {

	override fun areItemsTheSame(other: ListModel): Boolean {
		return other is StatsRecord && other.manga == manga
	}

	val time: ReadingTime

	init {
		val minutes = TimeUnit.MILLISECONDS.toMinutes(duration).toInt()
		time = ReadingTime(
			minutes = minutes % 60,
			hours = minutes / 60,
			isContinue = false,
		)
	}
}
