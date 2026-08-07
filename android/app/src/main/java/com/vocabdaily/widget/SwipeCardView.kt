package com.vocabdaily.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.widget.FrameLayout
import kotlin.math.abs

enum class SwipeDirection {
    LEFT,
    RIGHT,
}

/**
 * One swipe = one card. Supports left or right depending on [preferredDirection].
 */
class SwipeCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : FrameLayout(context, attrs) {

    var preferredDirection: SwipeDirection = SwipeDirection.LEFT
    var onSwiped: (() -> Unit)? = null

    /** @deprecated use [onSwiped] */
    var onSwipedLeft: (() -> Unit)?
        get() = onSwiped
        set(value) {
            onSwiped = value
        }

    private val touchSlop = ViewConfiguration.get(context).scaledTouchSlop
    private var downX = 0f
    private var downY = 0f
    private var activePointerId = MotionEvent.INVALID_POINTER_ID
    private var dragging = false
    private var animating = false
    private var gestureLocked = false

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (animating || gestureLocked) return true
        when (ev.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                activePointerId = ev.getPointerId(0)
                downX = ev.x
                downY = ev.y
                dragging = false
            }
            MotionEvent.ACTION_MOVE -> {
                val index = ev.findPointerIndex(activePointerId)
                if (index < 0) return false
                val dx = ev.getX(index) - downX
                val dy = ev.getY(index) - downY
                if (!dragging && abs(dx) > touchSlop && abs(dx) > abs(dy)) {
                    dragging = true
                    parent?.requestDisallowInterceptTouchEvent(true)
                    return true
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                activePointerId = MotionEvent.INVALID_POINTER_ID
                dragging = false
            }
        }
        return dragging
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (animating) return true
        if (gestureLocked && event.actionMasked != MotionEvent.ACTION_DOWN) return true

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                gestureLocked = false
                activePointerId = event.getPointerId(0)
                downX = event.x
                downY = event.y
                dragging = true
                parent?.requestDisallowInterceptTouchEvent(true)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                if (gestureLocked) return true
                val index = event.findPointerIndex(activePointerId)
                if (index < 0) return true
                val dx = event.getX(index) - downX
                translationX = when (preferredDirection) {
                    SwipeDirection.LEFT -> if (dx > 0) dx * 0.2f else dx
                    SwipeDirection.RIGHT -> if (dx < 0) dx * 0.2f else dx
                }
                rotation = (translationX / width.coerceAtLeast(1)) * 12f
                return true
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                if (gestureLocked) {
                    activePointerId = MotionEvent.INVALID_POINTER_ID
                    dragging = false
                    return true
                }
                val threshold = width * 0.25f
                val shouldSwipe = when (preferredDirection) {
                    SwipeDirection.LEFT -> translationX < -threshold
                    SwipeDirection.RIGHT -> translationX > threshold
                }
                if (shouldSwipe) {
                    animateSwipe()
                } else {
                    animate().setListener(null).translationX(0f).rotation(0f).setDuration(180).start()
                }
                activePointerId = MotionEvent.INVALID_POINTER_ID
                dragging = false
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    fun animateSwipeLeft() {
        preferredDirection = SwipeDirection.LEFT
        animateSwipe()
    }

    fun animateSwipeRight() {
        preferredDirection = SwipeDirection.RIGHT
        animateSwipe()
    }

    fun animateSwipe() {
        if (animating || gestureLocked) return
        animating = true
        gestureLocked = true
        parent?.requestDisallowInterceptTouchEvent(true)
        val distance = width.toFloat().coerceAtLeast(1f) + 120f
        val targetX = if (preferredDirection == SwipeDirection.LEFT) -distance else distance
        val targetRot = if (preferredDirection == SwipeDirection.LEFT) -16f else 16f
        animate()
            .translationX(targetX)
            .rotation(targetRot)
            .alpha(0.4f)
            .setDuration(200)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    animate().setListener(null)
                    translationX = 0f
                    rotation = 0f
                    alpha = 1f
                    animating = false
                    onSwiped?.invoke()
                }
            })
            .start()
    }

    fun unlockGesture() {
        gestureLocked = false
        dragging = false
        animating = false
        activePointerId = MotionEvent.INVALID_POINTER_ID
    }
}
