package org.koitharu.kotatsu.tv.scrobbling.common.domain

import okio.IOException
import org.koitharu.kotatsu.tv.scrobbling.common.domain.model.ScrobblerService

class ScrobblerAuthRequiredException(
	val scrobbler: ScrobblerService,
) : IOException()
