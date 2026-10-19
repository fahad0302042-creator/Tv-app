package org.koitharu.kotatsu.tv.settings.about

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import org.koitharu.kotatsu.tv.core.github.AppUpdateRepository
import org.koitharu.kotatsu.tv.core.github.AppVersion
import org.koitharu.kotatsu.tv.core.ui.BaseViewModel
import org.koitharu.kotatsu.tv.core.util.ext.MutableEventFlow
import org.koitharu.kotatsu.tv.core.util.ext.call
import javax.inject.Inject

@HiltViewModel
class AboutSettingsViewModel @Inject constructor(
	private val appUpdateRepository: AppUpdateRepository,
) : BaseViewModel() {

	val isUpdateSupported = flow {
		emit(appUpdateRepository.isUpdateSupported())
	}.stateIn(viewModelScope, SharingStarted.Eagerly, false)

	val onUpdateAvailable = MutableEventFlow<AppVersion?>()

	fun checkForUpdates() {
		launchLoadingJob {
			val update = appUpdateRepository.fetchUpdate()
			onUpdateAvailable.call(update)
		}
	}
}
