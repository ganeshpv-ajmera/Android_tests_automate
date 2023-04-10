package com.aop.interplay.ui.fragments.signup

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.aop.interplay.NavigationManager
import com.aop.interplay.R
import com.aop.interplay.databinding.FragmentSignUpBinding
import com.aop.interplay.ui.fragments.BaseFragment
import com.aop.interplay.ui.splash.WebViewBundle
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignUpFragment : BaseFragment() {
    private var binding: FragmentSignUpBinding? = null
    private lateinit var text: String
    private lateinit var spannableString: SpannableString
    @Inject lateinit var navigationManager: NavigationManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding=FragmentSignUpBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.navigation_dashboard)
                }
            })

        text = requireContext().getString(R.string.disclaimerTxt)

        spannableString = SpannableString(text)
        binding = FragmentSignUpBinding.bind(view)
        binding?.signUpMobileId?.setOnClickListener {
            findNavController().navigate(R.id.navigation_signupMobile)
        }

        makeTextSpanMove(R.id.webViewFragment, 32, 48, WebViewBundle.getTermsOfConditionsBundle())
        makeTextSpanMove(R.id.webViewFragment, 88, 102, WebViewBundle.getPrivacyPolicyBundle())

        initUI()

    }

    private fun initUI() {
        binding?.apply {
            btnNav.setOnClickListener {
                findNavController().navigate(R.id.navigation_dashboard)
            }
        }
    }

    private fun makeTextSpanMove(navigation: Int, startInd: Int, endInd: Int, bundle: Bundle) {
        spannableString.setSpan(clickableSpanLink(navigation, bundle), startInd, endInd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding?.disclaimerTxtId?.setText(spannableString, TextView.BufferType.SPANNABLE)
        binding?.disclaimerTxtId?.movementMethod = LinkMovementMethod.getInstance()
    }
}

//ClickableSpanLink function
fun Fragment.clickableSpanLink(nav: Int, bundle: Bundle): ClickableSpan {
    return object : ClickableSpan() {
        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.color = ContextCompat.getColor(requireContext(),R.color.descTextColor)
        }
        override fun onClick(widget: View) {
            findNavController().navigate(nav,bundle)
        }
    }
}


