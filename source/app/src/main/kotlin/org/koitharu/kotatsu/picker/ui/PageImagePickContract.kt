package org.koitharu.kotatsu.tv.picker.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract
import org.koitharu.kotatsu.tv.core.model.parcelable.ParcelableManga
import org.koitharu.kotatsu.tv.core.nav.AppRouter
import org.koitharu.kotatsu.tv.parsers.model.Manga

class PageImagePickContract : ActivityResultContract<Manga?, Uri?>() {

	override fun createIntent(context: Context, input: Manga?): Intent =
		Intent(context, PageImagePickActivity::class.java)
			.putExtra(AppRouter.KEY_MANGA, input?.let { ParcelableManga(it) })

	override fun parseResult(resultCode: Int, intent: Intent?): Uri? = intent?.data
}
