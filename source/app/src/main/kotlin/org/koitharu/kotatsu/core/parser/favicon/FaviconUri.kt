package org.koitharu.kotatsu.tv.core.parser.favicon

import android.net.Uri
import org.koitharu.kotatsu.tv.parsers.model.MangaSource

const val URI_SCHEME_FAVICON = "favicon"

fun MangaSource.faviconUri(): Uri = Uri.fromParts(URI_SCHEME_FAVICON, name, null)