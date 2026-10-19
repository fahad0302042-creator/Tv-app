package org.koitharu.kotatsu.tv.tracker.data

import org.koitharu.kotatsu.tv.core.db.entity.toManga
import org.koitharu.kotatsu.tv.core.db.entity.toMangaTags
import org.koitharu.kotatsu.tv.tracker.domain.model.TrackingLogItem
import java.time.Instant

fun TrackLogWithManga.toTrackingLogItem(): TrackingLogItem {
	val chaptersList = trackLog.chapters.split('\n').filterNot { x -> x.isEmpty() }
	return TrackingLogItem(
		id = trackLog.id,
		chapters = chaptersList,
		manga = manga.toManga(tags.toMangaTags(), null),
		createdAt = Instant.ofEpochMilli(trackLog.createdAt),
		isNew = trackLog.isUnread,
	)
}
