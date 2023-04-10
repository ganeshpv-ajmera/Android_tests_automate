package com.aop.interplay.ui.fragments.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aop.interplay.data.network.data.*
import com.aop.interplay.network.remote.NetworkRepository
import com.aop.interplay.network.remote.SignUpRepository
import com.aop.interplay.network.utils.NetworkResult
import com.aop.interplay.ui.fragments.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpRepository: SignUpRepository,
    networkRepository: NetworkRepository
) : BaseViewModel(networkRepository) {
    var id: Int = -1
    var mobileNumber: String = ""
    var webViewUrl: String = ""
    var webViewTitle: String = ""
    var birthday: String = ""
    var otp: String = ""
    var username: String = ""
    private var job: Job? = null
    private val resendOtpTimer: MutableLiveData<Int?> by lazy { MutableLiveData() }
    val otpLiveData: MutableLiveData<SignUpOTPResponse> by lazy { MutableLiveData() }
    private val otpErrorLiveData: MutableLiveData<String> by lazy { MutableLiveData() }
    val mobileNumberErrorLiveData: MutableLiveData<Int?> by lazy { MutableLiveData() }
    private val errorMessage: MutableLiveData<String> by lazy { MutableLiveData() }
    private val usernameErrorLiveData: MutableLiveData<Int> by lazy { MutableLiveData() }
    val otpVerification: MutableLiveData<SignUpVerifyOTPResponse> by lazy { MutableLiveData() }
    val userCreatedLiveData: MutableLiveData<SignUpUsernameCreatedResponse> by lazy { MutableLiveData() }
    val userUpdatedLiveData: MutableLiveData<SignUpUsernameUpdatedResponse> by lazy { MutableLiveData() }
    val usernameValidLiveData: MutableLiveData<List<SignUpUsernameValidationResponse>> by lazy { MutableLiveData() }

    private fun getOtp(mobileBody: SignUpMobileNumberBody) {
        viewModelScope.launch {
            signUpRepository.getOtp(mobileBody).collectLatest {
                when (it) {
                    is NetworkResult.Success -> otpLiveData.postValue(it.data)
                    is NetworkResult.Error -> mobileNumberErrorLiveData.postValue(it.responseCode)
                    is NetworkResult.Exception -> mobileNumberErrorLiveData.postValue(it.responseCode)
                }
            }
        }
    }

    private fun resendOtp(mobileBody: SignUpMobileNumberBody) {
        viewModelScope.launch {
            signUpRepository.getOtp(mobileBody).collectLatest {
                when (it) {
                    is NetworkResult.Success -> otpLiveData.postValue(it.data)
                    is NetworkResult.Error -> otpErrorLiveData.postValue(null)
                    is NetworkResult.Exception -> otpErrorLiveData.postValue(null)
                }
            }
        }
    }

    private fun verifyOtp(body: SignUpOTPBody) {
        viewModelScope.launch {
            signUpRepository.verifyOtp(body).collectLatest {
                when (it) {
                    is NetworkResult.Success -> otpVerification.postValue(it.data)
                    is NetworkResult.Error -> otpErrorLiveData.postValue(it.message)
                    is NetworkResult.Exception -> otpErrorLiveData.postValue(it.message)
                }
            }
        }
    }

    fun getOTPForMobileNumber(mobileNumber: String) {
        val mobNumber = "+1$mobileNumber"
        val body = SignUpMobileNumberBody(mobNumber)
        getOtp(body)
    }

    fun resendOTPForMobileNumber(mobileNumber: String) {
        val mobNumber = "+1$mobileNumber"
        val body = SignUpMobileNumberBody(mobNumber)
        resendOtp(body)
    }

    fun verifyMobileNumberAndOTP(otp: String, mobileNumber: String) {
        val mobNumber = "+1$mobileNumber"
        val body = SignUpOTPBody(otp, mobNumber)
        verifyOtp(body)
    }

    fun getOtpLiveData(): LiveData<SignUpOTPResponse> {
        return otpLiveData
    }

    fun getOtpErrorLiveData(): LiveData<String> {
        return otpErrorLiveData
    }

    fun getValidateUsernameErrorLiveData(): LiveData<Int> {
        return usernameErrorLiveData
    }

    fun getMobileNumberErrorLiveData(): LiveData<Int?> {
        return mobileNumberErrorLiveData
    }

    fun verifyOtpLiveData(): LiveData<SignUpVerifyOTPResponse> = otpVerification

    fun startTimer() {
        job?.let {
            if (it.isActive) return
        }
        job = CoroutineScope(Dispatchers.Default).launch {
            for (i in 60 downTo 0) {
                delay(1000)
                updateResendCodeTimer(i)
            }
        }
        job?.start()
    }

    private fun updateResendCodeTimer(i: Int) {
        resendOtpTimer.postValue(i)
    }

    fun resendOtpLiveData(): LiveData<Int?> = resendOtpTimer
    fun validateUsername(username: String) {
        viewModelScope.launch {
            signUpRepository.validateUsername(username).collectLatest {
                when (it) {
                    is NetworkResult.Success -> usernameValidLiveData.postValue(it.data)
                    is NetworkResult.Error -> usernameErrorLiveData.postValue(it.responseCode)
                    is NetworkResult.Exception -> otpErrorLiveData.postValue(it.message)
                }
            }
        }
    }

    fun createUser(username: String, birthday: String, id: Int) {
        val userBody = SignUpUserBody(
            username = username,
            dob = birthday,
        )
        setUser(userBody, id)
    }

    private fun setUser(userBody: SignUpUserBody, id: Int) {
        viewModelScope.launch {
            signUpRepository.setUser(userBody, id).collectLatest {
                when (it) {
                    is NetworkResult.Success -> userUpdatedLiveData.postValue(it.data)
                    is NetworkResult.Error -> errorMessage.postValue(it.message)
                    is NetworkResult.Exception -> otpErrorLiveData.postValue(it.message)
                }
            }
        }
    }

    fun clearError() {
        otpErrorLiveData.postValue(null)
    }
}