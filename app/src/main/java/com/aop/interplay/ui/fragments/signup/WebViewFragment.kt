package com.aop.interplay.ui.fragments.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.aop.interplay.databinding.FragmentWebViewBinding
import com.aop.interplay.ui.fragments.BaseFragment

class WebViewFragment : BaseFragment() {
    private var _binding: FragmentWebViewBinding? = null
    private val binding get() = _binding!!
    private val signUpViewModel by viewModels<SignUpViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentWebViewBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments
        bundle?.let {
            with(signUpViewModel) {
                webViewTitle = bundle.getString("title", "")
                webViewUrl = bundle.getString("url", "")
            }
        }
        initUI()
    }

    private fun initUI() {
        binding.apply {
            privacyPolicyId.topNavHandleId.webViewTitleId.text = signUpViewModel.webViewTitle
        }

        initListeners()
        initWebView()
    }

    private fun initWebView() {
        binding.apply {
            privacyPolicyId.webViewId.webViewClient = MyWebViewClient()
            privacyPolicyId.webViewId.loadUrl(signUpViewModel.webViewUrl)
        }
    }

    private fun initListeners() {
        binding.apply {
            privacyPolicyId.topNavHandleId.btnNav.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    inner class MyWebViewClient: WebViewClient(){
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            return false
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            binding.privacyPolicyId.webViewProgressBarId.visibility = View.GONE
        }
    }
}