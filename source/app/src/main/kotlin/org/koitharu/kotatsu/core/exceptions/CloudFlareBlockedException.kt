package org.koitharu.kotatsu.tv.core.exceptions

import org.koitharu.kotatsu.tv.core.model.UnknownMangaSource
import org.koitharu.kotatsu.tv.parsers.model.MangaSource
import org.koitharu.kotatsu.tv.parsers.network.CloudFlareHelper

class CloudFlareBlockedException(
	override val url: String,
	source: MangaSource?,
) : CloudFlareException("Blocked by CloudFlare", CloudFlareHelper.PROTECTION_BLOCKED) {

	override val source: MangaSource = source ?: UnknownMangaSource
}
