package org.koitharu.kotatsu.tv.core.util

import kotlinx.coroutines.CoroutineExceptionHandler
import org.koitharu.kotatsu.tv.core.util.ext.printStackTraceDebug
import org.koitharu.kotatsu.tv.core.util.ext.report
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

class AcraCoroutineErrorHandler : AbstractCoroutineContextElement(CoroutineExceptionHandler),
	CoroutineExceptionHandler {

	override fun handleException(context: CoroutineContext, exception: Throwable) {
		exception.printStackTraceDebug()
		exception.report()
	}
}
