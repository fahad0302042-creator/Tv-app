package org.koitharu.kotatsu.tv.core.exceptions

class IncompatiblePluginException(
	val name: String?,
	cause: Throwable?,
) : RuntimeException(cause)
