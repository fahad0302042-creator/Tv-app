package org.koitharu.kotatsu.tv.core.exceptions

import okio.IOException

class WrapperIOException(override val cause: Exception) : IOException(cause)
