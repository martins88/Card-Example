package eduardo.martins.card.example

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.SparseIntArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.cardview.widget.CardView

/**
 * @author Jose Martins
 * @since 2020-08-13
 */

class CustomCard(context: Context, attrs: AttributeSet) : CardView(context) {

    companion object {
        @Transient
        private val TYPE_A = 0

        @Transient
        private val TYPE_B = 1
    }

    private var mContext: Context? = null
    private var mCardType = 0

    init {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet) {
        mContext = context
        LayoutInflater.from(context).inflate(R.layout.custom_card, this, true)
        initAttrs(attrs)
        addMiddleContent()
    }

    private fun initAttrs(attrs: AttributeSet) {
        val a: TypedArray = mContext!!.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomCard,
            0,
            0
        )
        mCardType = try {
            a.getInteger(R.styleable.CustomCard_cardType, 0)
        } finally {
            a.recycle()
        }
    }

    @LayoutRes
    fun retrieveMiddleLayoutRes(cardType: Int): Int {
        val layouts = SparseIntArray()
        layouts.put(TYPE_A, R.layout.card_one)
        layouts.put(TYPE_B, R.layout.card_two)
        return layouts[cardType]
    }

    private fun addMiddleContent() {
        val root = findViewById<View>(R.id.customCard) as ViewGroup
        val placeholder: View = findViewById(R.id.placeholder)
        val layoutRes = retrieveMiddleLayoutRes(mCardType)
        val middleContent = LayoutInflater.from(mContext)
            .inflate(layoutRes, root, false)
        val params = placeholder.layoutParams
        middleContent.layoutParams = params
        root.removeView(placeholder)
        root.addView(middleContent)
    }

}