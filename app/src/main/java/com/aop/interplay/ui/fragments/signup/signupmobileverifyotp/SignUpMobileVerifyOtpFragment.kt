package com.aop.interplay.ui.fragments.signup.signupmobileverifyotp

import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.aop.interplay.R
import com.aop.interplay.custom_views.otpview.OtpView
import com.aop.interplay.databinding.FragmentSignUpMobileVerifyOtpBinding
import com.aop.interplay.ui.fragments.BaseFragment
import com.aop.interplay.ui.fragments.signup.SignUpViewModel
import com.aop.interplay.utils.AppConstants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpMobileVerifyOtpFragment : BaseFragment(), OtpView.OnOTpCompletedListener,
    OtpView.OnOtpInteractionListener {
    private var binding: FragmentSignUpMobileVerifyOtpBinding? = null
    private val viewModel: SignUpMobileVerifyOtpViewModel by viewModels()

    private val title = AppConstants.title

    private val signUpViewModel by viewModels<SignUpViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSignUpMobileVerifyOtpBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignUpMobileVerifyOtpBinding.bind(view)
        binding?.topNavHandleSignUpMobileVerifyId?.webViewTitleId?.text = title
        binding?.topNavHandleSignUpMobileVerifyId?.btnNav?.setOnClickListener {
            findNavController().navigateUp()
        }

        setState()

        binding?.otpViewId?.setOtpCompletedListener(this)
        binding?.otpViewId?.setOtpInteractionListener(this)

        binding?.verifyOtpId?.text =
            StringBuilder().append(requireContext().getString(R.string.signUpVerifyTxt)).append(" ")
                .append(arguments?.getString("MobileNumber"))
        val underlineMobileNum = SpannableString(binding?.verifyOtpId?.text)
        val start = underlineMobileNum.indexOf(arguments?.getString("MobileNumber").toString())
        val end = start + "underlined".length
        underlineMobileNum.setSpan(UnderlineSpan(), start, end, 0)
        binding?.verifyOtpId?.text = underlineMobileNum

        signUpViewModel.mobileNumber = arguments?.getString("MobileNumber").toString()

        binding?.changeMobileNumberId?.setOnClickListener {
            changeMobileNumber(arguments?.getString("MobileNumber"))
        }

        binding?.signUpVerifyNextId?.setOnClickListener {
            arguments?.getString("MobileNumber")
                ?.let { it1 -> signUpViewModel.verifyMobileNumberAndOTP(signUpViewModel.otp, it1) }
        }

        binding?.resendCodeId?.setOnClickListener {
            binding?.otpViewId?.resetState()
            binding?.signUpVerifyNextId?.isEnabled = false
            arguments?.getString("MobileNumber")?.let { it1 ->
                signUpViewModel.resendOTPForMobileNumber(it1)
            }
            launchCountDownTimer()
        }

        launchCountDownTimer()
        subscribeObservers()

        binding?.otpViewId?.isEnabled = true
    }

    private fun setState() {
        binding?.apply {
            otpViewId.setOtp(signUpViewModel.otp)
            if (signUpViewModel.otp != "") {
                binding?.signUpVerifyNextId?.isEnabled = true
            }
        }
    }

    private fun subscribeObservers() {
        signUpViewModel.resendOtpLiveData().observe(viewLifecycleOwner) {
            it?.let {
                updateResendCodeTime(it)
            }
        }

        signUpViewModel.otpVerification.observe(viewLifecycleOwner) {
            it?.let {
                signUpViewModel.id = it.id
                signUpViewModel.otpVerification.value = null
                binding?.verificationErrorTv?.visibility = View.GONE
                goToBirthDayScreen()
            }
        }

        signUpViewModel.getOtpErrorLiveData().observe(viewLifecycleOwner) {
            it?.let {
                binding?.root?.let {
                    binding?.verificationErrorTv?.visibility = View.VISIBLE
                    binding?.signUpVerifyNextId?.isEnabled = false
                    signUpViewModel.clearError()
                }
            }
        }

    }

    private fun updateResendCodeTime(seconds: Int) {
        binding?.resendCodeTimer?.text = getString(R.string.resend_otp_timer, seconds)
        if (seconds == 0) {
            binding?.resendCodeTimer?.visibility = View.GONE
            binding?.resendCodeId?.apply {
                isEnabled = true
                setTextColor(
                    ContextCompat.getColor(
                        requireContext(), R.color.btnSuccess
                    )
                )
            }
        }
    }


    private fun launchCountDownTimer() {
        binding?.resendCodeTimer?.text = getString(R.string.resend_otp_timer_start_time)
        binding?.resendCodeTimer?.visibility = View.VISIBLE
        binding?.resendCodeId?.apply {
            isEnabled = true
            setTextColor(
                ContextCompat.getColor(
                    requireContext(), R.color.textColor
                )
            )
        }
        signUpViewModel.startTimer()
    }


    private fun changeMobileNumber(mobilNumber: String?) {
        findNavController().navigateUp()
    }

    private fun goToBirthDayScreen() {
        val bundle = Bundle()
        bundle.putString("mobileNumber", signUpViewModel.mobileNumber)
        bundle.putInt("id", signUpViewModel.id)
        findNavController().navigate(
            R.id.action_navigation_singUpMobileOtpVerify_to_signUpBirthdayFragment,
            bundle
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        signUpViewModel.getOtpErrorLiveData().removeObservers(viewLifecycleOwner)
    }

    override fun onOtpCompleted(otp: String) {
        arguments?.getString("MobileNumber")?.let {
            signUpViewModel.otp = otp
            binding?.signUpVerifyNextId?.isEnabled = true
        }
    }

    override fun onOtpInteraction() {
        binding?.verificationErrorTv?.visibility = View.GONE
        binding?.signUpVerifyNextId?.isEnabled = false
    }

}


