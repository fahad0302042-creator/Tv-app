package org.koitharu.kotatsu.tv.scrobbling.common.domain.model

import javax.inject.Qualifier

@Qualifier
annotation class ScrobblerType(
	val service: ScrobblerService
)
