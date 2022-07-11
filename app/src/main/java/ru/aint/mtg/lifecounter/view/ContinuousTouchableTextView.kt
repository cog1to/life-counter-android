package ru.aint.mtg.lifecounter.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.MotionEvent
import ru.aint.mtg.lifecounter.R
import java.util.*
import kotlin.concurrent.fixedRateTimer

class ContinuousTouchableTextView(context: Context, attrs: AttributeSet): androidx.appcompat.widget.AppCompatTextView(context, attrs) {
    private var emitFirst: Boolean = false
    private var emitDelay: Long = 500
    private var emitInterval: Long = 100

    init {
        val attributeArray: TypedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.touchable, 0, 0
        )

        emitFirst = attributeArray.getBoolean(R.styleable.touchable_emitFirst, false)
        emitDelay = attributeArray.getInt(R.styleable.touchable_emitDelay, 500).toLong()
        emitInterval = attributeArray.getInt(R.styleable.touchable_emitInterval, 100).toLong()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                if (emitFirst) {
                    performClick()
                }
                startEmitTimer()
                isPressed = true
            }
            MotionEvent.ACTION_UP -> {
                stopEmitTimer()
                isPressed = false
            }
            else -> {
                return super.onTouchEvent(event)
            }
        }

        return true
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    // MARK: - Private

    private var emitLoop: Timer? = null

    private fun startEmitTimer() {
        val self = this
        emitLoop = fixedRateTimer(
            "touch-event-loop",
            initialDelay = emitDelay,
            period = emitInterval
        ) {
            self.handler.post { performClick() }
        }
    }

    private fun stopEmitTimer() {
        emitLoop?.cancel()
        emitLoop = null
    }
}