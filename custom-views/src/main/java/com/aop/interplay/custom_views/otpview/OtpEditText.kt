package com.aop.interplay.custom_views.otpview

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.view.inputmethod.InputConnectionWrapper
import androidx.appcompat.widget.AppCompatEditText


class OtpEditText(context: Context, attrs: AttributeSet) : AppCompatEditText(context, attrs) {
    var action: ((backPressed: String) -> Unit)? = null
    override fun onCreateInputConnection(outAttrs: EditorInfo): InputConnection {
        return CustomInputConnection(super.onCreateInputConnection(outAttrs), true)
    }

    inner class CustomInputConnection(target: InputConnection?, mutable: Boolean) :
        InputConnectionWrapper(target, mutable) {
        override fun sendKeyEvent(event: KeyEvent): Boolean {
            if (event.action == KeyEvent.ACTION_DOWN
                && event.keyCode == KeyEvent.KEYCODE_DEL
            ) {
                action?.invoke(text.toString())
            }
            return super.sendKeyEvent(event)
        }
    }
}