package org.koitharu.kotatsu.tv.core.exceptions

import okhttp3.Headers
import org.koitharu.kotatsu.tv.core.model.UnknownMangaSource
import org.koitharu.kotatsu.tv.parsers.model.MangaSource
import org.koitharu.kotatsu.tv.parsers.network.CloudFlareHelper

class CloudFlareProtectedException(
	override val url: String,
	source: MangaSource?,
	@Transient val headers: Headers,
) : CloudFlareException("Protected by CloudFlare", CloudFlareHelper.PROTECTION_CAPTCHA) {

	override val source: MangaSource = source ?: UnknownMangaSource
}
