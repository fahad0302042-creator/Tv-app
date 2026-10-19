package org.koitharu.kotatsu.tv.details.service

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.koitharu.kotatsu.tv.core.prefs.AppSettings

@EntryPoint
@InstallIn(SingletonComponent::class)
interface PrefetchCompanionEntryPoint {
	val settings: AppSettings
}
