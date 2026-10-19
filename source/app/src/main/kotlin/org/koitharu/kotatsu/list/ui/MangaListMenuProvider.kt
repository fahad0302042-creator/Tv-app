package org.koitharu.kotatsu.tv.list.ui

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import org.koitharu.kotatsu.tv.R
import org.koitharu.kotatsu.tv.core.nav.router
import org.koitharu.kotatsu.tv.favourites.ui.list.FavouritesListFragment
import org.koitharu.kotatsu.tv.history.ui.HistoryListFragment
import org.koitharu.kotatsu.tv.list.ui.config.ListConfigSection
import org.koitharu.kotatsu.tv.suggestions.ui.SuggestionsFragment
import org.koitharu.kotatsu.tv.tracker.ui.updates.UpdatesFragment

class MangaListMenuProvider(
	private val fragment: Fragment,
) : MenuProvider {

	override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
		menuInflater.inflate(R.menu.opt_list, menu)
	}

	override fun onMenuItemSelected(menuItem: MenuItem): Boolean = when (menuItem.itemId) {
		R.id.action_list_mode -> {
			val section: ListConfigSection = when (fragment) {
				is HistoryListFragment -> ListConfigSection.History
				is SuggestionsFragment -> ListConfigSection.Suggestions
				is FavouritesListFragment -> ListConfigSection.Favorites(fragment.categoryId)
				is UpdatesFragment -> ListConfigSection.Updated
				else -> ListConfigSection.General
			}
			fragment.router.showListConfigSheet(section)
			true
		}

		else -> false
	}
}
