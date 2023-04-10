package com.aop.interplay.ui.fragments.signup.signupusername

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.aop.interplay.NavigationManager
import com.aop.interplay.R
import com.aop.interplay.data.FullScreenDialogType
import com.aop.interplay.databinding.FragmentSignUpUsernameBinding
import com.aop.interplay.ui.fragments.BaseFragment
import com.aop.interplay.ui.fragments.signup.SignUpViewModel
import com.aop.interplay.utils.AppConstants
import dagger.hilt.android.AndroidEntryPoint
import java.net.HttpURLConnection
import javax.inject.Inject

@AndroidEntryPoint
class SignUpUserNameFragment : BaseFragment() {
    private var _binding: FragmentSignUpUsernameBinding? = null
    private val binding get() = _binding!!
    private val signUpViewModel by viewModels<SignUpViewModel>()

    @Inject
    lateinit var navigationManager: NavigationManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpUsernameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        binding.topNavHandleSignUpMobileId.webViewTitleId.text = AppConstants.title

        arguments?.let {
            signUpViewModel.mobileNumber = it.getString("mobileNumber", "")
            signUpViewModel.birthday = it.getString("dob", "")
            signUpViewModel.id = it.getInt("id", -1)
        }

        binding.apply {
            createUsernameId.requestFocus()
        }
        initListeners()
        subscribeObservers()
    }

    private fun subscribeObservers() {
        signUpViewModel.usernameValidLiveData.observe(viewLifecycleOwner) {
            it?.let {
                binding.mobileTxtInputLayout.isErrorEnabled = false
                binding.signUpNextMobileId.isEnabled = true
                binding.createUsernameId.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    ContextCompat.getDrawable(requireContext(), R.drawable.at_symbol),
                    null,
                    ContextCompat.getDrawable(requireContext(), R.drawable.correct),
                    null
                )
            }
        }

        signUpViewModel.userUpdatedLiveData.observe(viewLifecycleOwner) {
            it?.let {
                navigationManager.navigateToFullScreenDialogFragment(
                    title = getString(R.string.account_created_successfully_title),
                    message = getString(R.string.account_created_successfully_message),
                    ctaText1 = getString(R.string.account_created_successfully_cta1_text),
                    ctaText2 = getString(R.string.account_created_successfully_cta2_text),
                    fullScreenDialogType = FullScreenDialogType.ACCOUNT_CREATED
                )
            }
        }

        signUpViewModel.getValidateUsernameErrorLiveData().observe(viewLifecycleOwner) {
            it?.let {
                binding.mobileTxtInputLayout.isErrorEnabled = true
                if (it == HttpURLConnection.HTTP_INTERNAL_ERROR || it == HttpURLConnection.HTTP_NO_CONTENT) {
                    binding.mobileTxtInputLayout.error = getString(R.string.username_already_taken)
                } else {
                    binding.mobileTxtInputLayout.error = getString(R.string.username_already_taken)
                }
                binding.createUsernameId.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    ContextCompat.getDrawable(requireContext(), R.drawable.at_symbol),
                    null,
                    ContextCompat.getDrawable(requireContext(), R.drawable.wrong),
                    null
                )
                binding.signUpNextMobileId.isEnabled = false
            }
        }
    }

    private fun initListeners() {
        binding.topNavHandleSignUpMobileId.btnNav.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.signUpNextMobileId.setOnClickListener {
            createUser()
        }

        binding.createUsernameId.addTextChangedListener(UserNameTextWatcher())
    }

    private fun createUser() {
        signUpViewModel.createUser(
            signUpViewModel.username,
            signUpViewModel.birthday,
            signUpViewModel.id
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    inner class UserNameTextWatcher : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val userNameLength = s?.length
            userNameLength?.let {
                if (it <= 4) {
                    binding.mobileTxtInputLayout.error = getString(R.string.username_too_short)
                    binding.mobileTxtInputLayout.isErrorEnabled = true
                    binding.signUpNextMobileId.isEnabled = false

                    binding.createUsernameId.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        ContextCompat.getDrawable(requireContext(), R.drawable.at_symbol),
                        null,
                        null,
                        null
                    )
                } else {
                    binding.mobileTxtInputLayout.isErrorEnabled = false
                    signUpViewModel.username = s.toString()
                    validateUsername(signUpViewModel.username)
                }
            }
        }

        override fun afterTextChanged(s: Editable?) {

        }
    }

    private fun validateUsername(username: String) {
        signUpViewModel.validateUsername(username)
    }

}