package org.koitharu.kotatsu.tv.settings.search

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import org.koitharu.kotatsu.tv.R
import org.koitharu.kotatsu.tv.core.ui.list.AdapterDelegateClickListenerAdapter
import org.koitharu.kotatsu.tv.core.ui.list.OnListItemClickListener
import org.koitharu.kotatsu.tv.core.util.ext.textAndVisible
import org.koitharu.kotatsu.tv.databinding.ItemPreferenceBinding

fun settingsItemAD(
	listener: OnListItemClickListener<SettingsItem>,
) = adapterDelegateViewBinding<SettingsItem, SettingsItem, ItemPreferenceBinding>(
	{ layoutInflater, parent -> ItemPreferenceBinding.inflate(layoutInflater, parent, false) },
) {

	AdapterDelegateClickListenerAdapter(this, listener).attach()
	val breadcrumbsSeparator = getString(R.string.breadcrumbs_separator)

	bind {
		binding.textViewTitle.text = item.title
		binding.textViewSummary.textAndVisible = item.breadcrumbs.joinToString(breadcrumbsSeparator)
	}
}
