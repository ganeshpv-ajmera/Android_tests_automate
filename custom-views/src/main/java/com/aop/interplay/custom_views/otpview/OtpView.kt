package com.aop.interplay.custom_views.otpview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatEditText
import com.aop.interplay.custom_views.R

class OtpView(context: Context?, attrs: AttributeSet? = null) : LinearLayout(context, attrs) {
    private var otpBoxOne: OtpEditText? = null
    private var otpBoxTwo: OtpEditText? = null
    private var otpBoxThree: OtpEditText? = null
    private var otpBoxFour: OtpEditText? = null
    private var otpBoxFive: OtpEditText? = null
    private var otpBoxSix: OtpEditText? = null

    private var otpCompletedListener: OnOTpCompletedListener? = null
    private var otpInteractionListener: OnOtpInteractionListener? = null


    private val otpBoxList = arrayListOf<AppCompatEditText>()

    private val startingBox = 0
    private var currentBox = startingBox
    private val endingBox = 5

    init {
        inflate(context, R.layout.otp_view_layout, this)

        otpBoxOne = findViewById<View>(R.id.otp_box_one) as OtpEditText
        otpBoxTwo = findViewById<View>(R.id.otp_box_two) as OtpEditText
        otpBoxThree = findViewById<View>(R.id.otp_box_three) as OtpEditText
        otpBoxFour = findViewById<View>(R.id.otp_box_four) as OtpEditText
        otpBoxFive = findViewById<View>(R.id.otp_box_five) as OtpEditText
        otpBoxSix = findViewById<View>(R.id.otp_box_six) as OtpEditText


        initBoxes()
    }


    private fun initBoxes() {
        otpBoxOne?.apply {
            setText("")
            requestFocus()
            addTextChangedListener(OtpBoxTextWatcher())
            otpBoxList.add(0, this)
            action = {
                goBack(it)
            }
        }

        otpBoxTwo?.apply {
            setText("")
            addTextChangedListener(OtpBoxTextWatcher())
            otpBoxList.add(1, this)
            action = {
                goBack(it)
            }
        }

        otpBoxThree?.apply {
            setText("")
            addTextChangedListener(OtpBoxTextWatcher())
            otpBoxList.add(2, this)
            action = {
                goBack(it)
            }
        }

        otpBoxFour?.apply {
            setText("")
            addTextChangedListener(OtpBoxTextWatcher())
            otpBoxList.add(3, this)
            action = {
                goBack(it)
            }
        }

        otpBoxFive?.apply {
            setText("")
            addTextChangedListener(OtpBoxTextWatcher())
            otpBoxList.add(4, this)
            action = {
                goBack(it)
            }
        }

        otpBoxSix?.apply {
            setText("")
            addTextChangedListener(OtpBoxTextWatcher())
            otpBoxList.add(5, this)
            action = {
                goBack(it)
            }
        }
    }

    private fun goBack(text: String) {
        if (text.isEmpty()) {
            otpBoxList[currentBox].clearFocus()
            otpBoxList[currentBox].isFocusableInTouchMode = false
            if (currentBox != startingBox) --currentBox
            otpBoxList[currentBox].isFocusableInTouchMode = true
            otpBoxList[currentBox].requestFocus()
        } else {
            otpBoxList[currentBox].setText("")
        }
        otpInteractionListener?.onOtpInteraction()
    }

    fun resetState() {
        currentBox = 0
        otpBoxList.forEachIndexed { _, it ->
            it.setText("")
            it.isFocusableInTouchMode = false
        }
        if (otpBoxList.size > 0) {
            otpBoxList[0].isFocusableInTouchMode = true
            otpBoxList[0].requestFocus()
        }
    }

    fun setOtp(otp: String) {
        if (otp.length < 6) return
        for (i in otp.indices) {
            otpBoxList[i].setText(otp[i].toString())
            otpBoxList[i].isFocusableInTouchMode = false
        }
        if (otpBoxList.size == endingBox + 1) {
            otpBoxList[endingBox].isFocusableInTouchMode = true
            otpBoxList[endingBox].requestFocus()
        }
    }

    fun setOtpCompletedListener(onOTpCompletedListener: OnOTpCompletedListener) {
        otpCompletedListener = onOTpCompletedListener
    }

    fun setOtpInteractionListener(onOtpInteractionListener: OnOtpInteractionListener) {
        otpInteractionListener = onOtpInteractionListener
    }


    inner class OtpBoxTextWatcher : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(otp: CharSequence?, p1: Int, p2: Int, p3: Int) {
            otp?.let {
                if (otpBoxList[currentBox].text.toString().isEmpty()) {
                    return
                }
                if (currentBox != endingBox) {
                    otpInteractionListener?.onOtpInteraction()
                    otpBoxList[currentBox].isFocusableInTouchMode = false
                    ++currentBox
                    otpBoxList[currentBox].isFocusableInTouchMode = true
                    otpBoxList[currentBox].requestFocus()
                } else {
                    //OTP Completed
                    val stringBuilder = StringBuilder()
                    otpBoxList.forEach { editText ->
                        stringBuilder.append(editText.text.toString())
                    }
                    otpCompletedListener?.onOtpCompleted(stringBuilder.toString())
                }

            }
        }

        override fun afterTextChanged(p0: Editable?) {
        }
    }

    interface OnOTpCompletedListener {
        fun onOtpCompleted(otp: String)
    }

    interface OnOtpInteractionListener {
        fun onOtpInteraction()
    }
}