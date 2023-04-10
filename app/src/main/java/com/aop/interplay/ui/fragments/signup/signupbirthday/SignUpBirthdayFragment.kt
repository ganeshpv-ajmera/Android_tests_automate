package com.aop.interplay.ui.fragments.signup.signupbirthday

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.aop.interplay.NavigationManager
import com.aop.interplay.R
import com.aop.interplay.databinding.FragmentSignUpBirthdayBinding
import com.aop.interplay.ui.fragments.BaseFragment
import com.aop.interplay.ui.fragments.signup.SignUpViewModel
import com.aop.interplay.utils.AppConstants
import com.aop.interplay.utils.CalendarUtil
import com.aop.interplay.utils.Utils
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class SignUpBirthdayFragment : BaseFragment() {

    @Inject lateinit var navigationManager: NavigationManager

    private var _binding: FragmentSignUpBirthdayBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SignUpBirthdayViewModel by viewModels()
    private val signUpViewModel by viewModels<SignUpViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBirthdayBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        binding.topNavHandleSignUpMobileId.webViewTitleId.text = AppConstants.title

        arguments?.let {
            signUpViewModel.mobileNumber = it.getString("mobileNumber","")
            signUpViewModel.id = it.getInt("id",-1)
        }

        if(signUpViewModel.birthday != ""){
            binding.signUpNextMobileId.isEnabled = true
        }
        initListeners()
    }

    private fun initListeners() {
        binding.birthdayField.setOnFocusChangeListener { _, hasFocus ->
            if(hasFocus){
                showDatePicker()
                binding.birthdayField.clearFocus()
            }
        }

        binding.topNavHandleSignUpMobileId.btnNav.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.signUpNextMobileId.setOnClickListener {
//            navigationManager.navigateToFullScreenDialogFragment(
//                title = getString(R.string.account_created_successfully_title),
//                message = getString(R.string.account_created_successfully_message),
//                ctaText1 = getString(R.string.account_created_successfully_cta1_text),
//                ctaText2 = getString(R.string.account_created_successfully_cta2_text),
//                fullScreenDialogType = FullScreenDialogType.ACCOUNT_CREATED
//            )

            val bundle = Bundle()
            bundle.putString("mobileNumber",signUpViewModel.mobileNumber)
            bundle.putString("dob",signUpViewModel.birthday)
            bundle.putInt("id",signUpViewModel.id)
           findNavController().navigate(R.id.action_signUpBirthdayFragment_to_signUpUsernameFragment2,bundle)
       }
    }

    private fun showDatePicker() {
        val constraints = CalendarConstraints.Builder().setValidator(
            DateValidatorPointBackward.now()
        ).build()
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(getString(R.string.birthday))
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setCalendarConstraints(constraints)
            .build()
        initDatePickerListeners(datePicker)
        datePicker.show(parentFragmentManager,getString(R.string.birthday))

    }

    private fun initDatePickerListeners(picker: MaterialDatePicker<Long>) {
        picker.addOnPositiveButtonClickListener {
            it?.let {
                val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                calendar.timeInMillis = it
                val formattedDate = CalendarUtil.formatDate(calendar)
                setDate(formattedDate, it)
            }
        }
        picker.addOnNegativeButtonClickListener {

        }
        picker.addOnCancelListener {

        }
        picker.addOnDismissListener {

        }
    }

    private fun setDate(formattedDate: String, dateInMillis: Long) {
        binding.birthdayField.setText(formattedDate)
        signUpViewModel.birthday = formattedDate
        if(Utils.userAge(dateInMillis) >= AppConstants.REQUIRED_USER_AGE) {
            binding.mobileTxtInputLayout.isErrorEnabled = false
            enableNextButton()
        }
        else{
            binding.mobileTxtInputLayout.isErrorEnabled = true
            binding.mobileTxtInputLayout.error = getString(R.string.invalid_user_age)
        }
    }

    private fun enableNextButton() {
        binding.signUpNextMobileId.isEnabled = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}