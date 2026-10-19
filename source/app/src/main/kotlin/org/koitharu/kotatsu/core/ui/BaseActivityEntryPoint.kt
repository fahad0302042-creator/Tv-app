package org.koitharu.kotatsu.tv.core.ui

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.koitharu.kotatsu.tv.core.exceptions.resolve.ExceptionResolver
import org.koitharu.kotatsu.tv.core.prefs.AppSettings

@EntryPoint
@InstallIn(SingletonComponent::class)
interface BaseActivityEntryPoint {

	val settings: AppSettings

	val exceptionResolverFactory: ExceptionResolver.Factory
}
