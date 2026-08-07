package com.vocabdaily.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.widget.FrameLayout
import kotlin.math.abs

/**
 * Tinder-style drag: swipe left (or fling left) to dismiss and load the next card.
 */
class SwipeCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : FrameLayout(context, attrs) {

    var onSwipedLeft: (() -> Unit)? = null

    private val touchSlop = ViewConfiguration.get(context).scaledTouchSlop
    private var downX = 0f
    private var downY = 0f
    private var dragging = false
    private var animating = false

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        when (ev.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                downX = ev.x
                downY = ev.y
                dragging = false
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = ev.x - downX
                val dy = ev.y - downY
                if (!dragging && abs(dx) > touchSlop && abs(dx) > abs(dy)) {
                    dragging = true
                    parent?.requestDisallowInterceptTouchEvent(true)
                    return true
                }
            }
        }
        return dragging
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (animating) return true
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                downY = event.y
                dragging = true
                parent?.requestDisallowInterceptTouchEvent(true)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = event.x - downX
                val travel = if (dx > 0) dx * 0.25f else dx
                translationX = travel
                rotation = (travel / width.coerceAtLeast(1)) * 12f
                return true
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                val shouldSwipeLeft = translationX < -width * 0.22f
                if (shouldSwipeLeft) {
                    animateSwipeLeft()
                } else {
                    animate().translationX(0f).rotation(0f).setDuration(180).start()
                }
                dragging = false
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    fun animateSwipeLeft() {
        if (animating) return
        animating = true
        val distance = width.toFloat() + 80f
        animate()
            .translationX(-distance)
            .rotation(-18f)
            .alpha(0.35f)
            .setDuration(220)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    onSwipedLeft?.invoke()
                    translationX = 0f
                    rotation = 0f
                    alpha = 1f
                    animating = false
                }
            })
            .start()
    }
}
