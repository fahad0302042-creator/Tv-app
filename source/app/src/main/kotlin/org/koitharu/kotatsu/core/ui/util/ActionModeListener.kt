package org.koitharu.kotatsu.tv.core.ui.util

import androidx.appcompat.view.ActionMode

interface ActionModeListener {

	fun onActionModeStarted(mode: ActionMode)

	fun onActionModeFinished(mode: ActionMode)
}
