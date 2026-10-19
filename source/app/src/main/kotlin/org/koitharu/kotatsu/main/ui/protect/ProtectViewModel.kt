package org.koitharu.kotatsu.tv.main.ui.protect

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import org.koitharu.kotatsu.tv.core.exceptions.WrongPasswordException
import org.koitharu.kotatsu.tv.core.prefs.AppSettings
import org.koitharu.kotatsu.tv.core.ui.BaseViewModel
import org.koitharu.kotatsu.tv.core.util.ext.MutableEventFlow
import org.koitharu.kotatsu.tv.core.util.ext.call
import org.koitharu.kotatsu.tv.parsers.util.md5
import javax.inject.Inject

private const val PASSWORD_COMPARE_DELAY = 1_000L

@HiltViewModel
class ProtectViewModel @Inject constructor(
	private val settings: AppSettings,
	private val protectHelper: AppProtectHelper,
) : BaseViewModel() {

	private var job: Job? = null

	val onUnlockSuccess = MutableEventFlow<Unit>()

	val isBiometricEnabled
		get() = settings.isBiometricProtectionEnabled

	val isNumericPassword
		get() = settings.isAppPasswordNumeric

	fun tryUnlock(password: String) {
		if (job?.isActive == true) {
			return
		}
		job = launchLoadingJob {
			val passwordHash = password.md5()
			val appPasswordHash = settings.appPassword
			if (passwordHash == appPasswordHash) {
				unlock()
			} else {
				delay(PASSWORD_COMPARE_DELAY)
				throw WrongPasswordException()
			}
		}
	}

	fun unlock() {
		protectHelper.unlock()
		onUnlockSuccess.call(Unit)
	}
}
