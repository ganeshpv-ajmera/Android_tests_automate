package com.aop.interplay.extensions

import android.view.View
import androidx.core.view.isVisible

fun View.makeVisible() {
    isVisible = true
}

fun View.makeGone() {
    isVisible = false
}