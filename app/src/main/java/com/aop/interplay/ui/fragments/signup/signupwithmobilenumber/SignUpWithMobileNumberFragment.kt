package com.aop.interplay.ui.fragments.signup.signupwithmobilenumber

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.aop.interplay.R
import com.aop.interplay.databinding.FragmentSignUpWithMobileNumberBinding
import com.aop.interplay.ui.fragments.BaseFragment
import com.aop.interplay.ui.fragments.signup.SignUpViewModel
import com.aop.interplay.utils.AppConstants
import com.aop.interplay.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import java.net.HttpURLConnection

@AndroidEntryPoint
class SignUpWithMobileNumberFragment : BaseFragment() {
    private var binding: FragmentSignUpWithMobileNumberBinding? = null
    private var title = AppConstants.title

    private val viewModel: SignUpWithMobileNumberViewModel by viewModels()
    private val signUpViewModel by viewModels<SignUpViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSignUpWithMobileNumberBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.topNavHandleSignUpMobileId?.webViewTitleId?.text = title
        binding?.topNavHandleSignUpMobileId?.btnNav?.setOnClickListener {
            findNavController().navigateUp()
        }

        binding?.mobileTxtInputLayout?.requestFocus()
        binding?.mobileTxtInputLayout?.boxStrokeColor =
            ContextCompat.getColor(requireContext(), R.color.signUpTxtInputBorder)

        initializeState()
        initListeners()
        subscribeObservers()
    }

    private fun initListeners() {
        binding?.mobileNumberId?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val mobileNumber: String =
                    binding?.mobileTxtInputLayout?.editText?.text.toString().trim()
                if (mobileNumber.length != 10) {
                    binding?.mobileTxtInputLayout?.error = getString(R.string.invalid_mobile_number)
                } else {
                    binding?.mobileTxtInputLayout?.error = null
                    signUpViewModel.mobileNumber = mobileNumber
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val isValid = Utils.validateMobileNumber(s.toString())
                if (!isValid && s.toString().length != 10) {
                    binding?.signUpNextMobileId?.setOnClickListener(null)
                    binding?.signUpNextMobileId?.isEnabled = false
                } else if (isValid && s.toString().length == 10) {
                    binding?.signUpNextMobileId?.isEnabled = true
                    validateNextBtn()
                }
            }
        })
    }

    private fun subscribeObservers() {
        signUpViewModel.otpLiveData.observe(viewLifecycleOwner) {
            it?.let {
                val mobileNumberBundle = Bundle()
                val mobileNumberInput = binding?.mobileNumberId?.text
                mobileNumberBundle.putString("MobileNumber", mobileNumberInput.toString())
                findNavController().navigate(
                    R.id.navigation_singUpMobileOtpVerify, mobileNumberBundle
                )
            }
            signUpViewModel.otpLiveData.postValue(null)
        }

        signUpViewModel.mobileNumberErrorLiveData.observe(viewLifecycleOwner){
            it?.let {
                if(it == HttpURLConnection.HTTP_INTERNAL_ERROR){
                    binding?.apply {
                        mobileTxtInputLayout.isErrorEnabled = true
                        mobileTxtInputLayout.error = getString(R.string.mobile_number_already_exists)
                        signUpNextMobileId.isEnabled = false
                    }
                }
                signUpViewModel.mobileNumberErrorLiveData.postValue(null)
            }
        }
    }

    private fun initializeState() {
        binding?.apply {
            mobileNumberId.setText(signUpViewModel.mobileNumber)
        }
    }

    private fun changeNextBtnColor(btnBg: Int, color: Int) {
        binding?.signUpNextMobileId?.setBackgroundColor(btnBg)
        binding?.signUpNextMobileId?.setTextColor(color)
    }

    private fun validateNextBtn() {
        binding?.signUpNextMobileId?.setOnClickListener {
            signUpViewModel.getOTPForMobileNumber(signUpViewModel.mobileNumber.toString())
        }
    }
}
