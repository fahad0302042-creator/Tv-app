package org.koitharu.kotatsu.tv.browser.cloudflare

import org.koitharu.kotatsu.tv.browser.BrowserCallback

interface CloudFlareCallback : BrowserCallback {

	override fun onTitleChanged(title: CharSequence, subtitle: CharSequence?) = Unit

	fun onPageLoaded()

	fun onCheckPassed()

	fun onLoopDetected()
}
