package org.koitharu.kotatsu.core.ui.tv

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import org.koitharu.kotatsu.R

/**
 * Draws a high-contrast focus ring around the currently focused item of a [RecyclerView].
 *
 * Implemented as an [RecyclerView.ItemDecoration] so that *every* list in the app gets a
 * visible D-pad focus indicator without touching the ~150 item layouts, and without replacing
 * the `foreground` (ripple) any item view may already define.
 */
class TvFocusHighlightDecoration(
	context: Context,
) : RecyclerView.ItemDecoration() {

	private val inset = context.resources.getDimensionPixelSize(R.dimen.tv_focus_inset).toFloat()
	private val radius = context.resources.getDimensionPixelSize(R.dimen.tv_focus_corner).toFloat()
	private val rect = RectF()
	private val outerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
		style = Paint.Style.STROKE
		strokeWidth = context.resources.getDimensionPixelSize(R.dimen.tv_focus_stroke_outer).toFloat()
		color = ContextCompat.getColor(context, R.color.tv_focus_ring_outer)
	}
	private val innerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
		style = Paint.Style.STROKE
		strokeWidth = context.resources.getDimensionPixelSize(R.dimen.tv_focus_stroke_width).toFloat()
		color = ContextCompat.getColor(context, R.color.tv_focus_ring_inner)
	}

	override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
		val focused = parent.findFocus() ?: return
		if (focused === parent) {
			return
		}
		val item = findItemView(focused, parent) ?: return
		if (!item.isShown) {
			return
		}
		val dx = item.translationX
		val dy = item.translationY
		rect.set(
			item.left + inset + dx,
			item.top + inset + dy,
			item.right - inset + dx,
			item.bottom - inset + dy,
		)
		canvas.drawRoundRect(rect, radius, radius, outerPaint)
		rect.inset(outerPaint.strokeWidth / 2f, outerPaint.strokeWidth / 2f)
		canvas.drawRoundRect(rect, radius, radius, innerPaint)
	}

	private fun findItemView(view: View, parent: RecyclerView): View? {
		var current: View? = view
		while (current != null && current !== parent) {
			val lp = current.layoutParams
			if (lp is RecyclerView.LayoutParams && current.parent === parent) {
				return current
			}
			current = current.parent as? View
		}
		return null
	}
}
