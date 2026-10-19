package org.koitharu.kotatsu.tv.browser

interface BrowserCallback : OnHistoryChangedListener {

	fun onLoadingStateChanged(isLoading: Boolean)

	fun onTitleChanged(title: CharSequence, subtitle: CharSequence?)
}
