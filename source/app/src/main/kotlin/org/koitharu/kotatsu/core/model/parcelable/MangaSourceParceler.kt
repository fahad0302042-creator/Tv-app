package org.koitharu.kotatsu.tv.core.model.parcelable

import android.os.Parcel
import kotlinx.parcelize.Parceler
import org.koitharu.kotatsu.tv.core.model.MangaSource
import org.koitharu.kotatsu.tv.parsers.model.MangaSource

class MangaSourceParceler : Parceler<MangaSource> {

	override fun create(parcel: Parcel): MangaSource = MangaSource(parcel.readString())

	override fun MangaSource.write(parcel: Parcel, flags: Int) {
		parcel.writeString(name)
	}
}
