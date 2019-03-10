package octotentacle.yalauncher.views

import android.content.Context
import android.support.v7.widget.AppCompatImageView

import android.util.AttributeSet
import kotlin.math.min

class SquareImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val min = min(widthMeasureSpec, heightMeasureSpec)
        super.onMeasure(min, min)
    }
}