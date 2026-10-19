package org.koitharu.kotatsu.tv.core.exceptions

class SyncApiException(
	message: String,
	val code: Int,
) : RuntimeException(message)
