package org.koitharu.kotatsu.tv.favourites.ui

import android.os.Bundle
import org.koitharu.kotatsu.tv.core.nav.AppRouter
import org.koitharu.kotatsu.tv.core.ui.FragmentContainerActivity
import org.koitharu.kotatsu.tv.favourites.ui.list.FavouritesListFragment

class FavouritesActivity : FragmentContainerActivity(FavouritesListFragment::class.java) {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val categoryTitle = intent.getStringExtra(AppRouter.KEY_TITLE)
		if (categoryTitle != null) {
			title = categoryTitle
		}
	}
}
