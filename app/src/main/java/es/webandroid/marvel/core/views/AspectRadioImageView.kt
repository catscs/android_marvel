package es.webandroid.marvel.core.views

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import es.webandroid.marvel.R

class AspectRadioImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private var ratio: Float = 1f

    init {
        val attr = context.obtainStyledAttributes(attrs, R.styleable.AspectRadioImageView)
        ratio = attr.getFloat(R.styleable.AspectRadioImageView_ratio, 1f)
        attr.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        var width = measuredWidth
        var height = measuredHeight

        if (width == 0 && height == 0) return

        if (width > 0) {
            height = (width * ratio).toInt()
        } else if (height > 0) {
            width = (height / ratio).toInt()
        }

        setMeasuredDimension(width, height)
    }
}
