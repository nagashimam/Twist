package com.twist4english

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

typealias TouchOverride = (MotionEvent, (MotionEvent) -> Boolean) -> Boolean

class OneWaySwipePager(ctx: Context, attr: AttributeSet) : ViewPager(ctx, attr) {

    private var initialXValue: Float = 0.0f
    private var direction: SwipeDirection? = null

    private val touchOverride: TouchOverride = { event, action ->
        updateInitialX(event)
        if (isSwipeAllowed(event)) {
            action(event)
        } else {
            false
        }
    }

    // 障碍者が利用するのを想定していない。可能であれば、音声読み上げなどを実装するべき
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean =
        touchOverride(event) { motionEvent -> super.onTouchEvent(motionEvent) }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean =
        touchOverride(event) { motionEvent -> super.onInterceptTouchEvent(motionEvent) }

    private fun updateInitialX(event: MotionEvent) {
        if (event.action == MotionEvent.ACTION_DOWN) {
            initialXValue = event.x
        }
    }

    private fun isSwipeAllowed(event: MotionEvent): Boolean {
        if (direction == null) return false
        if (direction === SwipeDirection.ALL) return true
        if (direction === SwipeDirection.NONE) return false

        // 指が触れてから離れるまでのモーションだけ、右方向なのか左方向なのか判定する必要がある
        // それ以外はtrueを返し、処理を継続する
        if (event.action != MotionEvent.ACTION_MOVE) return true

        val diffX = event.x - initialXValue
        return if (direction === SwipeDirection.RIGHT) {
            diffX >= 0
        } else {
            diffX <= 0
        }
    }

    fun setDirection(direction: SwipeDirection){
        this.direction = direction
    }

}

enum class SwipeDirection {
    ALL,
    LEFT,
    RIGHT,
    NONE;
}