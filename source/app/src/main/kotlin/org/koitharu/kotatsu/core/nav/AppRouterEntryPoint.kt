package org.koitharu.kotatsu.tv.core.nav

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.koitharu.kotatsu.tv.core.prefs.AppSettings

@EntryPoint
@InstallIn(SingletonComponent::class)
interface AppRouterEntryPoint {

	val settings: AppSettings
}
