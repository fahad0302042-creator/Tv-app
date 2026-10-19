package org.koitharu.kotatsu.tv.details.domain

import org.koitharu.kotatsu.tv.core.util.LocaleStringComparator
import org.koitharu.kotatsu.tv.details.ui.model.MangaBranch

class BranchComparator : Comparator<MangaBranch> {

	private val delegate = LocaleStringComparator()

	override fun compare(o1: MangaBranch, o2: MangaBranch): Int = delegate.compare(o1.name, o2.name)
}
