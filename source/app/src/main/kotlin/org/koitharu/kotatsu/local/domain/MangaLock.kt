package org.koitharu.kotatsu.tv.local.domain

import org.koitharu.kotatsu.tv.core.util.MultiMutex
import org.koitharu.kotatsu.tv.parsers.model.Manga
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MangaLock @Inject constructor() : MultiMutex<Manga>()
