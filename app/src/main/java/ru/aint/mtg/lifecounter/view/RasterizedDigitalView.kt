package ru.aint.mtg.lifecounter.view

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import ru.aint.mtg.lifecounter.R

class RasterizedDigitalView(context: Context?, attrs: AttributeSet?): LinearLayout(context, attrs) {

    // MARK - Init

    private var digitViews: MutableList<ImageView> = arrayListOf()

    init {
        for (idx in 0 until NUMBER_OF_DIGITS) {
            val imageView = ImageView(context)

            imageView.adjustViewBounds = true
            imageView.scaleType = ImageView.ScaleType.FIT_XY
            imageView.setColorFilter(resources.getColor(R.color.button_tint));
            imageView.layoutParams = LayoutParams(
                resources.getDimensionPixelSize(R.dimen.digit_width),
                resources.getDimensionPixelSize(R.dimen.digit_height)
            )

            digitViews.add(imageView)
            addView(imageView)
        }
    }

    // MARK: - Public

    fun setText(text: String) {
        updateTo(text.take(Companion.NUMBER_OF_DIGITS))
    }

    // MARK: - Private

    private fun updateTo(text: String) {
        for (idx in 0 until NUMBER_OF_DIGITS) {
            if (idx < text.length) {
                digitViews[idx].visibility = View.VISIBLE
                val image = when (text[idx]) {
                    '0' -> R.drawable.digit_0
                    '1' -> R.drawable.digit_1
                    '2' -> R.drawable.digit_2
                    '3' -> R.drawable.digit_3
                    '4' -> R.drawable.digit_4
                    '5' -> R.drawable.digit_5
                    '6' -> R.drawable.digit_6
                    '7' -> R.drawable.digit_7
                    '8' -> R.drawable.digit_8
                    '9' -> R.drawable.digit_9
                    else -> throw NotImplementedError("Only digits are supported")
                }
                digitViews[idx].setImageBitmap(ImageCache.getImage(image, resources))
            } else {
                digitViews[idx].visibility = View.GONE
            }
        }
    }

    companion object {
        private const val NUMBER_OF_DIGITS: Int = 3
    }
}

object ImageCache {
    var images: MutableMap<Int, Bitmap> = mutableMapOf()

    fun getImage(id: Int, resources: Resources): Bitmap {
        images[id]?.let {
            return it
        } ?: run {
            val loaded = (ResourcesCompat.getDrawable(resources, id, null) as BitmapDrawable).bitmap
            images[id] = loaded!!
            return loaded
        }
    }
}