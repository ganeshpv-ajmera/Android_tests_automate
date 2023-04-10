package com.aop.interplay.custom_views.truncatingtextview

import android.content.Context
import android.graphics.Typeface
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.appcompat.widget.AppCompatTextView
import com.aop.interplay.custom_views.R

class TruncatingTextView : AppCompatTextView {

    private var fullText: CharSequence = ""
    private var initialMaxLines: Int = 0
    private var allowedMaxLines: Int = 1
    private var listener: OnTruncatingTextViewListener? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val obs = viewTreeObserver
                obs.removeGlobalOnLayoutListener(this)
                initialMaxLines = lineCount
                fullText = text
                movementMethod = LinkMovementMethod.getInstance()
                collapse()
            }
        })
    }

    private fun expand() {
        post {
            maxLines = initialMaxLines
            val actionString = context.getString(R.string.show_less)
            val suffix = "  $actionString"

            val actionDisplayText = "$fullText $suffix"

            val truncatedSpannableString = SpannableString(actionDisplayText)
            val startIndex = actionDisplayText.indexOf(actionString)
            val boldSpan = StyleSpan(Typeface.BOLD)
            truncatedSpannableString.setSpan(
                boldSpan,
                startIndex,
                startIndex + actionString.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            text = truncatedSpannableString

            setClickable(actionString, {
                collapse()
                listener?.onCollapseClicked()
            })
        }
    }

    private fun collapse() {
        post {
            if (lineCount > allowedMaxLines) {
                val lastCharShown = layout.getLineVisibleEnd(allowedMaxLines - 1)
                maxLines = allowedMaxLines
                val moreString = context.getString(R.string.show_more)
                val suffix = "  $moreString"

                val actionDisplayText = text.substring(
                    0, lastCharShown - suffix.length - 3
                ) + context.getString(R.string.ellipsis) + suffix
                val truncatedSpannableString = SpannableString(actionDisplayText)
                val startIndex = actionDisplayText.indexOf(moreString)
                val boldSpan = StyleSpan(Typeface.BOLD)
                truncatedSpannableString.setSpan(
                    boldSpan,
                    startIndex,
                    startIndex + moreString.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                text = truncatedSpannableString

                setClickable(moreString, {
                    listener?.onExpandClicked()
                    expand()
                })
            }
        }
    }

    private fun setClickable(
        subString: String, handler: () -> Unit, drawUnderline: Boolean = false
    ) {
        val start = text.indexOf(subString)
        val end = start + subString.length

        val span = SpannableString(text)
        span.setSpan(
            ClickHandler(handler, drawUnderline), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        linksClickable = true
        isClickable = true
        movementMethod = LinkMovementMethod.getInstance()

        text = span
    }

    private class ClickHandler(
        private val handler: () -> Unit, private val drawUnderline: Boolean
    ) : ClickableSpan() {
        override fun onClick(widget: View) {
            handler()
        }

        override fun updateDrawState(ds: TextPaint) {
            if (drawUnderline) {
                super.updateDrawState(ds)
            } else {
                ds.isUnderlineText = false
            }
        }
    }

    fun setListener(listener: OnTruncatingTextViewListener) {
        this.listener = listener
    }
}